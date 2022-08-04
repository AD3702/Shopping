package com.example.shopping.Database

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ProductDatum {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("gender")
    @Expose
    private var gender: String? = null

    @SerializedName("masterCategory")
    @Expose
    private var masterCategory: String? = null

    @SerializedName("subCategory")
    @Expose
    private var subCategory: String? = null

    @SerializedName("articleType")
    @Expose
    private var articleType: String? = null

    @SerializedName("baseColour")
    @Expose
    private var baseColour: String? = null

    @SerializedName("season")
    @Expose
    private var season: String? = null

    @SerializedName("year")
    @Expose
    private var year: String? = null

    @SerializedName("usage")
    @Expose
    private var usage: String? = null

    @SerializedName("productDisplayName")
    @Expose
    private var productDisplayName: String? = null

    @SerializedName("original_price")
    @Expose
    private var originalPrice: String? = null

    @SerializedName("discounted_price")
    @Expose
    private var discountedPrice: String? = null

    @SerializedName("total_discount")
    @Expose
    private var totalDiscount: String? = null

    @SerializedName("banner_image_id")
    @Expose
    private var bannerImageId: String? = null

    @SerializedName("banner_image_url")
    @Expose
    private var bannerImageUrl: String? = null

    @SerializedName("banner_image_type")
    @Expose
    private var bannerImageType: String? = null

    @SerializedName("banner_article_type")
    @Expose
    private var bannerArticleType: String? = null

    @SerializedName("banner_gender")
    @Expose
    private var bannerGender: String? = null

    @SerializedName("wishlist_product_id")
    @Expose
    private var wishListProductId: String? = null

    @SerializedName("wishlist_user_id")
    @Expose
    private var wishListUserId: String? = null

    @SerializedName("added_to_wish_list")
    @Expose
    private var addedToWishList: Boolean? = null

    fun getWishListUserId(): String? {
        return wishListUserId
    }

    fun setWishListUserId(wishListUserId: String?) {
        this.wishListUserId = wishListUserId
    }

    fun getAddedToWishList(): Boolean? {
        return addedToWishList
    }

    fun setAddedToWishList(addedToWishList: Boolean?) {
        this.addedToWishList = addedToWishList
    }

    fun getWishListProductId(): String? {
        return wishListProductId
    }

    fun setWishListProductId(wishListProductId: String?) {
        this.wishListProductId = wishListProductId
    }

    @SerializedName("COUNT(id)")
    @Expose
    private var cOUNTId: String? = null


    fun getCOUNTId(): String? {
        return cOUNTId
    }

    fun setCOUNTId(cOUNTId: String?) {
        this.cOUNTId = cOUNTId
    }

    fun getBannerArticleType(): String? {
        return bannerArticleType
    }

    fun setBannerArticleType(bannerArticleType: String?) {
        this.bannerArticleType = bannerArticleType
    }

    fun getBannerGender(): String? {
        return bannerGender
    }

    fun setBannerGender(bannerImageId: String?) {
        this.bannerGender = bannerGender
    }

    fun getBannerImageId(): String? {
        return bannerImageId
    }

    fun setBannerImageId(bannerImageId: String?) {
        this.bannerImageId = bannerImageId
    }

    fun getBannerImageUrl(): String? {
        return bannerImageUrl
    }

    fun setBannerImageUrl(bannerImageUrl: String?) {
        this.bannerImageUrl = bannerImageUrl
    }

    fun getBannerImageType(): String? {
        return bannerImageType
    }

    fun setBannerImageType(bannerImageType: String?) {
        this.bannerImageType = bannerImageType
    }


    fun getOriginalPrice(): String? {
        return originalPrice
    }

    fun getDiscountedPrice(): String? {
        return discountedPrice
    }

    fun getTotalDiscount(): String? {
        return totalDiscount
    }


    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String?) {
        this.gender = gender
    }

    fun getMasterCategory(): String? {
        return masterCategory
    }

    fun setMasterCategory(masterCategory: String?) {
        this.masterCategory = masterCategory
    }

    fun getSubCategory(): String? {
        return subCategory
    }

    fun setSubCategory(subCategory: String?) {
        this.subCategory = subCategory
    }

    fun getArticleType(): String? {
        return articleType
    }

    fun setArticleType(articleType: String?) {
        this.articleType = articleType
    }

    fun getBaseColour(): String? {
        return baseColour
    }

    fun setBaseColour(baseColour: String?) {
        this.baseColour = baseColour
    }

    fun getSeason(): String? {
        return season
    }

    fun setSeason(season: String?) {
        this.season = season
    }

    fun getYear(): String? {
        return year
    }

    fun setYear(year: String?) {
        this.year = year
    }

    fun getUsage(): String? {
        return usage
    }

    fun setUsage(usage: String?) {
        this.usage = usage
    }

    fun getProductDisplayName(): String? {
        return productDisplayName
    }

    fun setProductDisplayName(productDisplayName: String?) {
        this.productDisplayName = productDisplayName
    }


}