package com.example.shopping.Database

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Products {
    @SerializedName("productData")
    @Expose
    private var productData: List<ProductDatum?>? = null

    fun getProductData(): List<ProductDatum?>? {
        return productData
    }

    fun setProductData(productData: List<ProductDatum?>?) {
        this.productData = productData
    }
}