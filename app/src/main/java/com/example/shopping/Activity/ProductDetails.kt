package com.example.shopping.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Adapter.ProductDetailsAdapter
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ProductDetails : AppCompatActivity() {
    lateinit var productRecyclerView: RecyclerView
    var productDetailsArrayListTemp: ArrayList<ProductDatum> = ArrayList()
    lateinit var progressBar: ProgressBar
    lateinit var headerTextView: TextView
    lateinit var backImageDetails: ImageView
    lateinit var wishList: ImageView

    var productDetailsArrayList: ArrayList<ProductDatum> = ArrayList()
    lateinit var productDetailsGender: ProductDetails
    var wishListArrayList: ArrayList<ProductDatum> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details_gender)
        productRecyclerView = findViewById(R.id.product_recycler_gender)
        progressBar = findViewById(R.id.product_progress_gender)
        headerTextView = findViewById(R.id.details_header_text)
        backImageDetails = findViewById(R.id.details_back)
        wishList = findViewById(R.id.wishlist_product_details)
        wishList.setOnClickListener {
            var intentPut = Intent(this@ProductDetails, WishListActivity::class.java)
            Log.e("getWishList", intent.getStringExtra("gender").toString())
            intentPut.putExtra("gender", intent.getStringExtra("gender").toString())
            intentPut.putExtra("articleType", intent.getStringExtra("articleType").toString())
            intentPut.putExtra("imageType", intent.getStringExtra("imageType").toString())
            intentPut.putExtra("intent_from", "prod_details")
            startActivity(intentPut)
            finish()
        }
        productDetailsGender = this@ProductDetails
        backImageDetails.setOnClickListener {
            onBackPressed()
        }

        if (intent.getStringExtra("imageType").equals("top_row")) {
            Log.e(
                "getProductsDetailsFromArticleGender",
                intent.getStringExtra("imageType").toString()
            )
            headerTextView.text = intent.getStringExtra("articleType").toString()
            var call: Call<Products?>? = APIInterface.createLocal().getProductsFromArticleGender(
                "getProductsDetailsFromArticleGender",
                intent.getStringExtra("articleType"),
                intent.getStringExtra("gender")
            )
            call!!.enqueue(object : Callback<Products?> {
                override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                    try {
                        progressBar.visibility = View.INVISIBLE
                        productDetailsArrayList =
                            response.body()!!.getProductData() as ArrayList<ProductDatum>
                        getWishList()
                    } catch (e: Exception) {

                    }
                }

                override fun onFailure(call: Call<Products?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } else if (intent.getStringExtra("imageType").equals("articleType")) {
            Log.e(
                "getProductsDetailsFromArticleGender",
                intent.getStringExtra("imageType").toString()
            )
            productDetailsArrayListTemp = ArrayList()
            var loop = 0
            var articleType =
                productDetailsGender.intent.getStringExtra("articleType")
                    .toString()
            var articleToken = StringTokenizer(articleType, ",")
            var tempArticleTokenizer = StringTokenizer(articleType, ",")
            try {
                headerTextView.text =
                    "${tempArticleTokenizer.nextToken()} & ${tempArticleTokenizer.nextToken()}"
            } catch (e: Exception) {
                headerTextView.text = intent.getStringExtra("articleType").toString()
            }
            while (loop < articleToken.countTokens()) {
                var call: Call<Products?>? =
                    APIInterface.createLocal().getProductsFromArticleGender(
                        "getProductsDetailsFromArticleGender",
                        articleToken.nextToken().toString(),
                        intent.getStringExtra("gender")
                    )
                call!!.enqueue(object : Callback<Products?> {
                    override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                        try {
                            productDetailsArrayListTemp =
                                response.body()!!.getProductData() as ArrayList<ProductDatum>
                            productDetailsArrayList.addAll(productDetailsArrayListTemp)
                            productDetailsArrayList.shuffle()
                            progressBar.visibility = View.GONE
                            getWishList()
                        } catch (e: Exception) {
                            Log.e(
                                "getProductsDetailsFromArticleGender",
                                e.toString()
                            )
                        }
                    }

                    override fun onFailure(call: Call<Products?>, t: Throwable) {

                    }

                })
                loop++
            }
        } else if (intent.getStringExtra("imageType").equals("masterCategory")) {
            headerTextView.text = intent.getStringExtra("articleType").toString()
            var call: Call<Products?>? = APIInterface.createLocal().getMasterData(
                "getMasterData",
                intent.getStringExtra("articleType"),
            )
            Log.e(
                "getProductsDetailsFromArticleGender",
                intent.getStringExtra("imageType").toString()
            )
            Log.e(
                "getProductsDetailsFromArticleGender",
                intent.getStringExtra("articleType").toString()
            )
            call!!.enqueue(object : Callback<Products?> {
                override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                    try {
                        progressBar.visibility = View.INVISIBLE
                        productDetailsArrayList =
                            response.body()!!.getProductData() as ArrayList<ProductDatum>
                        productDetailsArrayList.shuffle()
                        getWishList()
                    } catch (e: Exception) {

                    }
                }

                override fun onFailure(call: Call<Products?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } else if (intent.getStringExtra("imageType").equals("usage")) {
            headerTextView.text = intent.getStringExtra("articleType").toString()
            Log.e(
                "getProductsDetailsFromArticleGender",
                intent.getStringExtra("articleType").toString()
            )
            var call: Call<Products?>? = APIInterface.createLocal().getUsageData(
                "getUsageData",
                intent.getStringExtra("articleType"),
                intent.getStringExtra("gender")
            )
            call!!.enqueue(object : Callback<Products?> {
                override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                    try {
                        progressBar.visibility = View.INVISIBLE
                        productDetailsArrayList =
                            response.body()!!.getProductData() as ArrayList<ProductDatum>
                        productDetailsArrayList.shuffle()
                        getWishList()
                    } catch (e: Exception) {

                    }
                }

                override fun onFailure(call: Call<Products?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } else if (intent.getStringExtra("imageType").equals("HomeGrid")) {
            headerTextView.text = intent.getStringExtra("articleType").toString()
            var call: Call<Products?>? = APIInterface.createLocal().getProductsFromArticle(
                "getProductsDetailsFromArticle",
                intent.getStringExtra("articleType")
            )
            call!!.enqueue(object : Callback<Products?> {
                override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                    try {
                        progressBar.visibility = View.INVISIBLE
                        productDetailsArrayList =
                            response.body()!!.getProductData() as ArrayList<ProductDatum>
                        getWishList()
                    } catch (e: Exception) {

                    }
                }

                override fun onFailure(call: Call<Products?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } else if (intent.getStringExtra("imageType").equals("HomeSlider")) {
            var bannerGender: String = intent.getStringExtra("gender").toString()
            var bannerArticle: String = intent.getStringExtra("article").toString()
            val bannerGenderStringTokenizer = StringTokenizer(bannerGender, ",")
            Log.e("ArticleType", "Total Gender: " + bannerGenderStringTokenizer.countTokens())
            var loopGender = 0
            while (loopGender <= bannerGenderStringTokenizer.countTokens()) {
                var gender = bannerGenderStringTokenizer.nextToken()
                Log.e("ArticleType", bannerArticle)
                Log.e("ArticleType", gender)
                var call: Call<Products?>? = APIInterface.createLocal()
                    .getProductsFromArticleGender(
                        "getProductsDetailsFromArticleGender",
                        bannerArticle,
                        gender
                    )
                call!!.enqueue(object : Callback<Products?> {
                    override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                        try {
                            progressBar.visibility = View.GONE
                            productDetailsArrayListTemp =
                                response.body()!!.getProductData() as ArrayList<ProductDatum>
                            productDetailsArrayList.addAll(productDetailsArrayListTemp)
                            productDetailsArrayList.shuffle()
                            getWishList()
                        } catch (e: Exception) {

                        }
                    }

                    override fun onFailure(call: Call<Products?>, t: Throwable) {
                        Log.e("ArticleType", t.toString())
                    }

                })
                loopGender++
            }
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    fun getWishList() {
        var callList: Call<Products?>? =
            APIInterface.createLocal().getWishList("getWishList", "1")
        callList!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                try {
                    wishListArrayList =
                        response.body()!!.getProductData() as ArrayList<ProductDatum>
                    var loop: Int = 0
                    var innerLoop: Int = 0
//                    Log.e("getWishList", wishListArrayList.size.toString())
                    while (loop < wishListArrayList.size) {
                        while (innerLoop < productDetailsArrayList.size) {
                            if (productDetailsArrayList[innerLoop].getId()
                                    .equals(wishListArrayList[loop].getWishListProductId())
                            ) {
                                productDetailsArrayList[innerLoop].setAddedToWishList(true)
                                break
                            }
                            innerLoop++
                        }
                        Log.e(
                            "getWishList",
                            productDetailsArrayList[innerLoop].getId().toString() + "-" +
                                    wishListArrayList[loop].getWishListProductId()
                                        .toString()
                        )
                        loop++
                    }
                    var productDetailsAdapter =
                        ProductDetailsAdapter(
                            this@ProductDetails,
                            productDetailsArrayList
                        )
                    productRecyclerView.layoutManager =
                        LinearLayoutManager(this@ProductDetails)
                    productRecyclerView.adapter = productDetailsAdapter
                } catch (e: Exception) {
                    Log.e("getWishList", e.toString())
                    var productDetailsAdapter =
                        ProductDetailsAdapter(
                            this@ProductDetails,
                            productDetailsArrayList
                        )
                    productRecyclerView.layoutManager =
                        LinearLayoutManager(this@ProductDetails)
                    productRecyclerView.adapter = productDetailsAdapter
                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {
                Log.e("getWishList", t.toString())
            }

        })
    }

}