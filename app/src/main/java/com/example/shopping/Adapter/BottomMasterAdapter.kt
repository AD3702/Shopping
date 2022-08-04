package com.example.shopping.Adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BottomMasterAdapter(
    private var context: Context,
    productsArrayList: ArrayList<ProductDatum>,
    gender: String,
) : RecyclerView.Adapter<BottomMasterAdapter.ViewHolder>() {

    var productsArrayList: ArrayList<ProductDatum>
    var gender: String

    init {
        this.context = context
        this.productsArrayList = productsArrayList
        this.gender = gender
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainCategory: TextView
        var subCategoryRecyclerView: RecyclerView
        var expandMore: ImageView
        var expandLess: ImageView

        init {
            mainCategory = itemView.findViewById(R.id.masterCategoryTextBottom)
            subCategoryRecyclerView = itemView.findViewById(R.id.subCategoryRecycler)
            expandMore = itemView.findViewById(R.id.expand_master_category)
            expandLess = itemView.findViewById(R.id.compress_master_category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.bottom_master_categories, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        holder.mainCategory.text = productDatum.getMasterCategory().toString()
        var callSub: Call<Products?>? = APIInterface.createLocal().getSubCategoryFromMaster(
            "getSubCategoryFromMaster",
            productDatum.getMasterCategory().toString(), gender
        )
        callSub!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                try {
                    var subCategoryList: ArrayList<ProductDatum> =
                        response.body()!!.getProductData() as ArrayList<ProductDatum>
                    var subCategoryAdapter =
                        SubCategoryAdapter(context, subCategoryList, gender)
                    holder.subCategoryRecyclerView.layoutManager = LinearLayoutManager(context)
                    holder.subCategoryRecyclerView.adapter = subCategoryAdapter
                } catch (e: Exception) {

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {

            }
        })
        holder.expandMore.setOnClickListener {
            holder.expandMore.visibility = View.GONE
            holder.expandLess.visibility = View.VISIBLE
            holder.subCategoryRecyclerView.visibility = View.VISIBLE
            val animSlideDown: Animation = AnimationUtils.loadAnimation(
                context,
                R.anim.slide_down
            )
            holder.subCategoryRecyclerView.startAnimation(animSlideDown)
        }
        holder.expandLess.setOnClickListener {
            holder.expandLess.visibility = View.GONE
            holder.expandMore.visibility = View.VISIBLE
            val animSlideDown: Animation = AnimationUtils.loadAnimation(
                context,
                R.anim.slide_up
            )
            holder.subCategoryRecyclerView.startAnimation(animSlideDown)
            val mainHandler = Handler(Looper.myLooper()!!).postDelayed({
                holder.subCategoryRecyclerView.visibility = View.GONE
            }, 100)

        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

}