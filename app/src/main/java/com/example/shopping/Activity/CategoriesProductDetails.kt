package com.example.shopping.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmoreexample.OnLoadMoreListener
import com.example.shopping.Adapter.*
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesProductDetails : AppCompatActivity() {

    lateinit var backBtn: ImageView
    lateinit var subCategoryRecyclerArticleDetails: RecyclerView
    lateinit var productDetailsAdapter: ProductDetailsAdapter
    lateinit var intentStr: String
    lateinit var wishList: ImageView

    companion object {
        lateinit var subCategoryRecyclerProductDetails: RecyclerView
        lateinit var categoriesProductDetails: CategoriesProductDetails
        lateinit var progressBar: ProgressBar
    }

    lateinit var subTypeArrayList: ArrayList<ProductDatum>
    lateinit var articleTypeArrayList: ArrayList<ProductDatum>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_product_details)
        backBtn = findViewById(R.id.back_category_product_details)
        intentStr = intent.getStringExtra("category").toString()
        backBtn.setOnClickListener {
            onBackPressed()
        }

        wishList = findViewById(R.id.wishlist_categories_prod)
        wishList.setOnClickListener {
            startActivity(Intent(this@CategoriesProductDetails, WishListActivity::class.java))
        }
        categoriesProductDetails = this@CategoriesProductDetails
        progressBar = findViewById(R.id.categoriesProductDetailsLoader)
        subCategoryRecyclerArticleDetails = findViewById(R.id.subCategoryRecyclerArticleDetails)
        subCategoryRecyclerProductDetails = findViewById(R.id.subCategoryRecyclerProductDetails)
        var callSub: Call<Products?>? = APIInterface.createLocal().getArticleFromSubCategory(
            "getArticleFromSubCategory",
            intent.getStringExtra("subCategory").toString(),
            intent.getStringExtra("gender").toString()
        )
        callSub!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                try {
                    subTypeArrayList =
                        response.body()!!.getProductData() as ArrayList<ProductDatum>
                    Log.e(
                        "getProductsDetailsFromArticleGender",
                        subTypeArrayList.size.toString()
                    )
                    if (subTypeArrayList.size > 1) {
                        var subCategoryAdapter =
                            SubCategoryTopRow(
                                this@CategoriesProductDetails,
                                subTypeArrayList
                            )
                        subCategoryRecyclerArticleDetails.layoutManager =
                            LinearLayoutManager(
                                this@CategoriesProductDetails,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        subCategoryRecyclerArticleDetails.adapter = subCategoryAdapter
                    }
                    var callArticle: Call<Products?>? = APIInterface.createLocal()
                        .getProductsFromArticleGender(
                            "getProductsDetailsFromArticleGender",
                            subTypeArrayList[0].getArticleType(),
                            intent.getStringExtra("gender").toString()
                        )
                    callArticle!!.enqueue(object : Callback<Products?> {
                        override fun onResponse(
                            call: Call<Products?>,
                            response: Response<Products?>
                        ) {
                            progressBar.visibility = View.INVISIBLE
                            articleTypeArrayList =
                                response.body()!!.getProductData() as ArrayList<ProductDatum>
                            Log.e(
                                "getProductsDetailsFromArticleGender",
                                articleTypeArrayList.size.toString()
                            )
                            productDetailsAdapter = ProductDetailsAdapter(
                                this@CategoriesProductDetails,
                                articleTypeArrayList
                            )
                            subCategoryRecyclerProductDetails.layoutManager =
                                LinearLayoutManager(this@CategoriesProductDetails)
                            subCategoryRecyclerProductDetails.adapter = productDetailsAdapter
                        }

                        override fun onFailure(call: Call<Products?>, t: Throwable) {

                        }

                    })
                } catch (e: Exception) {

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {

            }

        })
    }

    override fun onBackPressed() {
        finish()
    }


}