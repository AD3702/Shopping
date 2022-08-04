package com.example.shopping.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Activity.CategoriesActivity
import com.example.shopping.Activity.CategoriesProductDetails
import com.example.shopping.Database.ProductDatum
import com.example.shopping.R

class SubCategoryAdapter(
    private var context: Context,
    productsArrayList: ArrayList<ProductDatum>,
    gender: String,
) : RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {

    var productsArrayList: ArrayList<ProductDatum>
    var gender: String

    init {
        this.context = context
        this.productsArrayList = productsArrayList
        this.gender = gender
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subCategory: TextView

        init {
            subCategory = itemView.findViewById(R.id.subCategoryTextBottom)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.sub_category_bottom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        holder.subCategory.text = productDatum.getSubCategory().toString()
        holder.subCategory.setOnClickListener {
            var intent = Intent(context, CategoriesProductDetails::class.java)
            intent.putExtra("subCategory", productDatum.getSubCategory().toString())
            intent.putExtra("gender", gender)
            intent.putExtra("category", gender)
            Log.e("OnBackPressed", gender.toString())
            context.startActivity(intent)
//            CategoriesActivity.categoriesActivity.finish()
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }
}