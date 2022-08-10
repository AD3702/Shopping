package com.example.shopping.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Activity.CategoriesProductDetails
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SubCategoryTopRow(
    private var context: Context,
    productsArrayList: ArrayList<ProductDatum>
) : RecyclerView.Adapter<SubCategoryTopRow.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.sub_category_top_row, parent, false)
        return ViewHolder(view)
    }

    var productsArrayList: ArrayList<ProductDatum>
    var articleTypeArrayListTemp: ArrayList<ProductDatum> = ArrayList()

    init {
        this.context = context
        this.productsArrayList = productsArrayList
    }


    @SuppressLint("SetTextI18n")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textSubCategory: TextView
        var cardSubCategory: MaterialCardView

        init {
            textSubCategory = itemView.findViewById(R.id.text_sub_category)
            cardSubCategory = itemView.findViewById(R.id.card_sub_category)
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        holder.textSubCategory.text = productDatum.getArticleType()
        holder.cardSubCategory.setOnClickListener {
            CategoriesProductDetails.progressBar.visibility = View.VISIBLE
            CategoriesProductDetails.subCategoryRecyclerProductDetails.visibility = View.GONE
            var callArticle: Call<Products?>? = APIInterface.createLocal()
                .getProductsFromArticleGender(
                    "getProductsDetailsFromArticleGender",
                    productDatum.getArticleType(),
                    CategoriesProductDetails.categoriesProductDetails.intent.getStringExtra("gender")
                        .toString()
                )

            callArticle!!.enqueue(object : Callback<Products?> {
                override fun onResponse(
                    call: Call<Products?>,
                    response: Response<Products?>
                ) {
                    articleTypeArrayListTemp =
                        response.body()!!.getProductData() as ArrayList<ProductDatum>
                    articleTypeArrayListTemp.shuffle()
                    CategoriesProductDetails.progressBar.visibility = View.GONE
                    CategoriesProductDetails.subCategoryRecyclerProductDetails.visibility =
                        View.VISIBLE
                    var productDetailsAdapter =
                        ProductDetailsAdapter(
                            context,
                            articleTypeArrayListTemp
                        )
//                    CategoriesProductDetails.subCategoryRecyclerProductDetails.isNestedScrollingEnabled =
//                        false
                    CategoriesProductDetails.subCategoryRecyclerProductDetails.layoutManager =
                        LinearLayoutManager(context)
                    CategoriesProductDetails.subCategoryRecyclerProductDetails.adapter =
                        productDetailsAdapter
                }

                override fun onFailure(call: Call<Products?>, t: Throwable) {

                }

            })
        }
    }

}