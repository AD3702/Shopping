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

class BannerImagesAdapter(
    private var context: Context,
    productsArrayList: ArrayList<ProductDatum>,
) :
    RecyclerView.Adapter<BannerImagesAdapter.ViewHolder>() {

    var productsArrayList: ArrayList<ProductDatum>

    init {
        this.context = context
        this.productsArrayList = productsArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.banner_image_layout, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainImage: ImageView

        init {
            mainImage = itemView.findViewById(R.id.banner_image_category)
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        Picasso.get()
            .load(APIInterface.LOCAL_URL + productDatum.getBannerImageUrl())
            .into(holder.mainImage)
        Log.e(
            "ImageURL",
            APIInterface.LOCAL_URL + productDatum.getBannerImageUrl()
        )

        var articleType: String = productDatum.getBannerArticleType().toString()
        Log.e("ArticleType", articleType)

        var bannerImage: String = productDatum.getBannerImageType().toString()
        Log.e("ArticleType", bannerImage)

        var gender: String = productDatum.getBannerGender().toString()
        Log.e("ArticleType", gender)

        holder.mainImage.setOnClickListener {
            var intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("imageType", bannerImage)
            intent.putExtra("articleType", articleType)
            intent.putExtra("gender", gender)
            context.startActivity(intent)
        }

    }

}