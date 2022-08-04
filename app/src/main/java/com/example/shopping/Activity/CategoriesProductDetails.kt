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

//    private fun setItemsData() {
//        articleTypeArrayListTemp = ArrayList()
//        for (i in 0..10) {
//            articleTypeArrayListTemp.add(articleTypeArrayList[i])
//        }
//    }
//
//    private fun setAdapter() {
//        productDetailsAdapter = ProductDetailsAdapter(this, articleTypeArrayList)
//        productDetailsAdapter.notifyDataSetChanged()
//        subCategoryRecyclerProductDetails.adapter = productDetailsAdapter
//    }
//
//    private fun setRVLayoutManager() {
//        mLayoutManager = LinearLayoutManager(this)
//        subCategoryRecyclerProductDetails.layoutManager = mLayoutManager
//        subCategoryRecyclerProductDetails.setHasFixedSize(true)
//    }
//
//    private fun setRVScrollListener() {
//        mLayoutManager = LinearLayoutManager(this)
//        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
//        scrollListener.setOnLoadMoreListener(object :
//            OnLoadMoreListener {
//            override fun onLoadMore() {
//                LoadMoreData()
//            }
//        })
//        subCategoryRecyclerProductDetails.addOnScrollListener(scrollListener)
//    }
//
//    private fun LoadMoreData() {
//        productDetailsAdapter.addLoadingView()
//        loadMoreItemsCells = ArrayList()
//        val start = productDetailsAdapter.itemCount
//        val end = start + 16
//
//        Handler().postDelayed({
//            for (i in start..end) {
//
//                loadMoreItemsCells.add(articleTypeArrayList[i])
//            }
//
//            productDetailsAdapter.removeLoadingView()
//
//            productDetailsAdapter.addData(loadMoreItemsCells)
//
//            scrollListener.setLoaded()
//
//            subCategoryRecyclerProductDetails.post {
//                productDetailsAdapter.notifyDataSetChanged()
//            }
//        }, 3000)
//
//    }

    override fun onBackPressed() {
//        var intent = Intent(this@CategoriesProductDetails, CategoriesActivity::class.java)
//        Log.e("OnBackPressed", intentStr)
//        intent.putExtra("category", intentStr)
//        startActivity(intent)
        finish()
    }


}