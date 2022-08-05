package com.example.shopping.Database

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIInterface {
    companion object {

//        var LOCAL_URL = "http://172.20.10.4/shopping/"
        var LOCAL_URL = "http://192.168.1.63/shopping/"
//        var LOCAL_URL = "http://192.168.1.7/shopping/"

        //        var LOCAL_URL = "https://shopperzproject.000webhostapp.com/shopperz/"
        var IMAGE_URL = "https://shopperz.kiraattechnology.com/"

//        fun create(): APIInterface {
//
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(BASE_URL)
//                .build()
//            return retrofit.create(APIInterface::class.java)
//
//        }

        fun createLocal(): APIInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(LOCAL_URL)
                .build()
            return retrofit.create(APIInterface::class.java)

        }

    }

    @FormUrlEncoded
    @POST("API.php")
    fun getDistinctMasterData(
        @Field("function") function: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getCompleteProductPrice(
        @Field("function") function: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getDistinctGender(
        @Field("function") function: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getSliderImages(
        @Field("function") function: String?,
        @Field("banner_image_type") banner_image_type: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getCount(
        @Field("function") function: String?,
        @Field("masterCategory") masterCategory: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getMasterData(
        @Field("function") function: String?,
        @Field("masterCategory") masterCategory: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getUsageData(
        @Field("function") function: String?,
        @Field("usage") usage: String?,
        @Field("gender") gender: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getArticleFromMaster(
        @Field("function") function: String?,
        @Field("masterCategory") masterCategory: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getArticleData(
        @Field("function") function: String?,
        @Field("articleType") articleType: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getArticleByGender(
        @Field("function") function: String?,
        @Field("gender") gender: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getMasterByGender(
        @Field("function") function: String?,
        @Field("gender") gender: String?
    ): Call<Products?>?


    @FormUrlEncoded
    @POST("API.php")
    fun getSubCategoryFromMaster(
        @Field("function") function: String?,
        @Field("masterCategory") masterCategory: String?,
        @Field("gender") gender: String?,
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getBannerImages(
        @Field("function") function: String?,
        @Field("banner_gender") banner_gender: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getProductsFromArticle(
        @Field("function") function: String?,
        @Field("articleType") articleType: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getArticleFromSubCategory(
        @Field("function") function: String?,
        @Field("subCategory") articleType: String?,
        @Field("gender") gender: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getProductsFromArticleGender(
        @Field("function") function: String?,
        @Field("articleType") articleType: String?,
        @Field("gender") gender: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun addToWishList(
        @Field("function") function: String?,
        @Field("wishlist_user_id") wishlist_user_id: String?,
        @Field("wishlist_product_id") wishlist_product_id: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun removeFromList(
        @Field("function") function: String?,
        @Field("wishlist_user_id") wishlist_user_id: String?,
        @Field("wishlist_product_id") wishlist_product_id: String?
    ): Call<Products?>?

    @FormUrlEncoded
    @POST("API.php")
    fun getWishList(
        @Field("function") function: String?,
        @Field("wishlist_user_id") wishlist_user_id: String?
    ): Call<Products?>?

}