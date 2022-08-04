package com.example.shopping.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.shopping.Activity.ProductDetails
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class SliderAdapterHomeBanner(context: Context) :
    SliderViewAdapter<SliderAdapterHomeBanner.SliderAdapterVH>() {
    private var mSliderItems: ArrayList<SliderItem> = ArrayList()

    var context: Context

    init {
        this.context = context
    }


    fun renewItems(sliderItems: ArrayList<SliderItem>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SliderItem) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = mSliderItems[position]
        Picasso.get().load(sliderItem.getImageUrl()).into(viewHolder.imageViewBackground)
        viewHolder.itemView.setOnClickListener {
            var bannerArticle: String = sliderItem.getBannerArticleType().toString()
            var bannerGender: String = sliderItem.getBannerGender().toString()
            var intent = Intent(context, ProductDetails::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            intent.putExtra("gender", bannerGender)
            intent.putExtra("article", bannerArticle)
            intent.putExtra("imageType", "HomeSlider")
            context.startActivity(intent)
        }
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) :
        ViewHolder(itemView) {
        var imageViewBackground: ImageView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
        }
    }
}
