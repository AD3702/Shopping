package com.example.shopping.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Activity.ProductDetails
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.R
import com.squareup.picasso.Picasso

class CategoryTopRowAdapter(
    private var context: Context,
    productsArrayList: ArrayList<ProductDatum>,
    masterTypeStr: String
) :
    RecyclerView.Adapter<CategoryTopRowAdapter.ViewHolder>() {

    var productsArrayList: ArrayList<ProductDatum>
    private var masterTypeStr: String

    init {
        this.context = context
        this.productsArrayList = productsArrayList
        this.masterTypeStr = masterTypeStr
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.category_top_row_layout, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainImage: ImageView
        var mainCategory: TextView
        var layout: LinearLayout

        init {
            mainImage = itemView.findViewById(R.id.top_recycler_image_category)
            mainCategory = itemView.findViewById(R.id.top_recycler_text_category)
            layout = itemView.findViewById(R.id.category_top_row_header_layout)
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        Picasso.get()
            .load(APIInterface.LOCAL_URL + "top_row_images/" + masterTypeStr + "/" + productDatum.getArticleType() + ".png")
            .into(holder.mainImage)
        holder.mainCategory.text = productDatum.getArticleType().toString()
        Log.e(
            "ImageURL",
            APIInterface.LOCAL_URL + "top_row_images/" + masterTypeStr + "/" + productDatum.getArticleType() + ".png"
        )
        holder.layout.setOnClickListener {
            var intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("gender", masterTypeStr)
            intent.putExtra("articleType", productDatum.getArticleType())
            intent.putExtra("imageType","top_row")
            context.startActivity(intent)
        }
    }

}