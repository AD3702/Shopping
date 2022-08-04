package com.example.shopping.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.AsyncTask
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmoreexample.Constant
import com.example.shopping.Activity.ProductDetails
import com.example.shopping.Activity.WishListActivity
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductDetailsWishListAdapter(
    private var context: Context,
    productsArrayList: ArrayList<ProductDatum>
) :
    RecyclerView.Adapter<ProductDetailsWishListAdapter.ViewHolder>() {

    var productsArrayList: ArrayList<ProductDatum>
    var wishListArrayListTemp: ArrayList<ProductDatum> = ArrayList()

    lateinit var progressbar: ProgressBar

    init {
        this.context = context
        this.productsArrayList = productsArrayList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailsWishListAdapter.ViewHolder {
        context = parent.context

        if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.product_recycler_layout, parent, false)
            return ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.progress_loading, parent, false)
            progressbar = view.findViewById(R.id.progressbar)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                progressbar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                progressbar.indeterminateDrawable.setColorFilter(
                    Color.WHITE,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            return ViewHolder(view)
        }
    }

    @SuppressLint("SetTextI18n")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var name: TextView
        var totalPrice: TextView
        var totalDiscount: TextView
        var discountPrice: TextView
        var getItBy: TextView
        var freeDelivery: TextView
        var addToWishList: ImageView
        var removeFromList: ImageView

        init {
            imageView = itemView.findViewById(R.id.product_image)
            name = itemView.findViewById(R.id.product_name)
            totalPrice = itemView.findViewById(R.id.total_price_product)
            totalDiscount = itemView.findViewById(R.id.total_discount_product)
            discountPrice = itemView.findViewById(R.id.discounted_price_product)
            getItBy = itemView.findViewById(R.id.getItBy)
            freeDelivery = itemView.findViewById(R.id.freeDelivery)
            addToWishList = itemView.findViewById(R.id.add_to_wish_list_product_recycler)
            removeFromList = itemView.findViewById(R.id.remove_from_wish_list_product_recycler)
            removeFromList.visibility = View.VISIBLE
            addToWishList.visibility = View.GONE
//            var productDatum: ProductDatum = productsArrayList[adapterPosition]
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var productDatum: ProductDatum = productsArrayList[position]



        Picasso.get().load(APIInterface.IMAGE_URL + "images/" + productDatum.getId() + ".jpg")
            .into(holder.imageView);
        if (position == 0) {
            Log.e("ImageURL", APIInterface.IMAGE_URL + "images/" + productDatum.getId() + ".jpg")
        }
        holder.name.text = productDatum.getProductDisplayName()
        holder.totalPrice.paintFlags = holder.totalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        var totalPriceStr = holder.totalPrice.text.toString()
        var totalPriceInt: Float = totalPriceStr.toFloat()

        var discountedPriceStr = holder.discountPrice.text.toString()
        var discountedPriceInt: Float = discountedPriceStr.toFloat()


        var totalDiscountPer: Float = (100 - (discountedPriceInt / totalPriceInt) * 100)

        holder.totalDiscount.text = "(${String.format("%.2f", totalDiscountPer)}% off)"

        val cal = Calendar.getInstance()
        val monthDate = SimpleDateFormat("MMM")
        val monthName: String = monthDate.format(cal.time)

        val dateOfMonth = cal.get(Calendar.DAY_OF_MONTH).toString()

        holder.getItBy.text = "tomorrow, $monthName $dateOfMonth"

        if (discountedPriceInt < 1500) {
            holder.freeDelivery.visibility = View.VISIBLE
        } else {
            holder.freeDelivery.visibility = View.INVISIBLE
        }
        holder.removeFromList.setOnClickListener {
            holder.addToWishList.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.wishlist
                )
            )
            var callAdd: Call<Products?>? = APIInterface.createLocal()
                .removeFromList("removeFromList", "1", productDatum.getId().toString())
            callAdd!!.enqueue(object : Callback<Products?> {
                override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                    try {
                        wishListArrayListTemp =
                            response.body()!!.getProductData() as ArrayList<ProductDatum>
                        var productDetailsAdapter =
                            ProductDetailsWishListAdapter(
                                context,
                                wishListArrayListTemp
                            )
                        WishListActivity.wishRecyclerView.layoutManager =
                            LinearLayoutManager(context)
                        WishListActivity.wishRecyclerView.adapter = productDetailsAdapter
                    } catch (e: Exception) {
                        WishListActivity.wishListActivity.recreate()
                    }
                }

                override fun onFailure(call: Call<Products?>, t: Throwable) {

                }

            })
        }

    }


}