package com.example.shopping.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Activity.CategoriesActivity
import com.example.shopping.Activity.ProductDetails
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.R
import com.squareup.picasso.Picasso

class TopRowAdapter(private var context: Context, productsArrayList: ArrayList<ProductDatum>) :
    RecyclerView.Adapter<TopRowAdapter.ViewHolder>() {

    lateinit var productsArrayList: ArrayList<ProductDatum>

    init {
        this.context = context
        this.productsArrayList = productsArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.home_top_row_layout, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainImage: ImageView
        var mainCategory: TextView
        var topRowLayout: LinearLayout

        init {
            mainImage = itemView.findViewById(R.id.top_recycler_image)
            mainCategory = itemView.findViewById(R.id.top_recycler_text)
            topRowLayout = itemView.findViewById(R.id.top_row_header_layout)
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        var intent: Intent
        if (!productDatum.getMasterCategory().equals(null)) {
            Picasso.get()
                .load(APIInterface.LOCAL_URL + "top_row_images/Home/" + productDatum.getMasterCategory() + ".webp")
                .into(holder.mainImage)
            holder.mainCategory.text = productDatum.getMasterCategory().toString()
            Log.e(
                "ImageURL",
                APIInterface.LOCAL_URL + "top_row_images/Home/" + productDatum.getMasterCategory() + ".webp"
            )
            intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("articleType", productDatum.getMasterCategory())
            intent.putExtra("imageType", "masterCategory")

        } else {
            Picasso.get()
                .load(APIInterface.LOCAL_URL + "top_row_images/Home/" + productDatum.getGender() + ".webp")
                .into(holder.mainImage)
            holder.mainCategory.text = productDatum.getGender().toString()
            Log.e(
                "ImageURL",
                APIInterface.LOCAL_URL + "top_row_images/Home/" + productDatum.getGender() + ".webp"
            )
            intent = Intent(context, CategoriesActivity::class.java)
            intent.putExtra("category", productDatum.getGender())
        }
        holder.topRowLayout.setOnClickListener {
            context.startActivity(intent)
        }

    }

}