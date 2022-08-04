package com.example.shopping.Adapter

import android.annotation.SuppressLint
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SliderItem {
    private var description: String? = null
    private var imageUrl: String? = null
    private var bannerArticleType: String? = null
    private var bannerGender: String? = null


    fun getBannerArticleType(): String? {
        return bannerArticleType
    }

    fun setBannerArticleType(bannerArticleType: String?) {
        this.bannerArticleType = bannerArticleType
    }

    fun getBannerGender(): String? {
        return bannerGender
    }

    fun setBannerGender(bannerGender: String?) {
        this.bannerGender = bannerGender
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
    }
}