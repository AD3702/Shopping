package com.example.shopping.Activity

import android.content.Intent
import android.hardware.lights.Light
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Adapter.BannerImagesAdapter
import com.example.shopping.Adapter.BottomMasterAdapter
import com.example.shopping.Adapter.CategoryTopRowAdapter
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesActivity : AppCompatActivity() {
    lateinit var logoText: TextView
    lateinit var backBtn: ImageView
    lateinit var moreBtn: ImageView
    lateinit var categoryList: ArrayList<ProductDatum>
    lateinit var imageList: ArrayList<ProductDatum>
    lateinit var categoryRecyclerView: RecyclerView
    lateinit var bannerImageAdapter: RecyclerView
    lateinit var wishList: ImageView

    companion object {
        lateinit var categoriesActivity: CategoriesActivity
        lateinit var progressBar: ProgressBar
    }

    var tempList: ArrayList<ProductDatum> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catagories)
        logoText = findViewById(R.id.logo_text_categories)
        backBtn = findViewById(R.id.back_category)
        moreBtn = findViewById(R.id.more_option_categories)
        categoriesActivity = this@CategoriesActivity
        logoText.text = intent.getStringExtra("category")
        categoryRecyclerView = findViewById(R.id.category_recycler)
        bannerImageAdapter = findViewById(R.id.category_banner_recycler)
        backBtn.setOnClickListener {
            onBackPressed()
        }

        wishList = findViewById(R.id.wishlist_categories)
        wishList.setOnClickListener {
            startActivity(Intent(this@CategoriesActivity, WishListActivity::class.java))
        }

        moreBtn.setOnClickListener {
            bottomCategories()
        }

        var call: Call<Products?>? =
            APIInterface.createLocal()
                .getArticleByGender("getDistinctArticleByGender", intent.getStringExtra("category"))
        call!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                try {
                    categoryList = response.body()!!.getProductData() as ArrayList<ProductDatum>
                    var loop = 0
                    if (intent.getStringExtra("category").equals("Men")) {
                        while (loop < categoryList.size) {
                            if (categoryList[loop].getArticleType()
                                    .equals("Tshirts") || categoryList[loop].getArticleType()
                                    .equals("Shirts") || categoryList[loop].getArticleType()
                                    .equals("Jeans") || categoryList[loop].getArticleType()
                                    .equals("Shorts") || categoryList[loop].getArticleType()
                                    .equals("Casual Shoes")
                            ) {
                                tempList.add(categoryList[loop])
                            }
                            loop++
                        }
                    } else if (intent.getStringExtra("category").equals("Women")) {
                        while (loop < categoryList.size) {
                            if (categoryList[loop].getArticleType()
                                    .equals("Dresses") || categoryList[loop].getArticleType()
                                    .equals("Sarees") || categoryList[loop].getArticleType()
                                    .equals("Kurtas") || categoryList[loop].getArticleType()
                                    .equals("Handbags") || categoryList[loop].getArticleType()
                                    .equals("Heels") || categoryList[loop].getArticleType()
                                    .equals("Shorts")
                            ) {
                                tempList.add(categoryList[loop])
                            }
                            loop++
                        }
                    } else if (intent.getStringExtra("category").equals("Girls")) {
                        while (loop < categoryList.size) {
                            if (categoryList[loop].getArticleType()
                                    .equals("Skirts") || categoryList[loop].getArticleType()
                                    .equals("Tops") || categoryList[loop].getArticleType()
                                    .equals("Backpacks") || categoryList[loop].getArticleType()
                                    .equals("Handbags") || categoryList[loop].getArticleType()
                                    .equals("Capris") || categoryList[loop].getArticleType()
                                    .equals("Caps")
                            ) {
                                tempList.add(categoryList[loop])
                            }
                            loop++
                        }
                    }
                    setTopAdapter()
                    Log.e("CategoriesData", "S")
                } catch (e: Exception) {
                    Log.e("CategoriesData", e.toString())
                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {
                Log.e("CategoriesData", "F")
            }

        })

        var callBanner: Call<Products?>? = APIInterface.createLocal()
            .getBannerImages("getImagesByGender", intent.getStringExtra("category"))
        callBanner!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                try {
                    imageList = response.body()!!.getProductData() as ArrayList<ProductDatum>
                    setImageBannerAdapter()
                } catch (e: Exception) {

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {

            }

        })

    }

    fun setImageBannerAdapter() {
        var bannerImagesAdapter =
            BannerImagesAdapter(
                this@CategoriesActivity,
                imageList,
            )
        bannerImageAdapter.layoutManager =
            LinearLayoutManager(
                this@CategoriesActivity
            )
        bannerImageAdapter.adapter = bannerImagesAdapter
    }

    fun setTopAdapter() {
        var categoryTopRowAdapter =
            CategoryTopRowAdapter(
                this@CategoriesActivity,
                tempList,
                intent.getStringExtra("category").toString()
            )
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(
                this@CategoriesActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        categoryRecyclerView.adapter = categoryTopRowAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun bottomCategories() {
        val dialog = BottomSheetDialog(this@CategoriesActivity)
        val view = layoutInflater.inflate(R.layout.categories_filter_bottom, null)
        val btnClose = view.findViewById<ImageView>(R.id.close_button_filter_bottom_categories)
        progressBar = view.findViewById<ProgressBar>(R.id.bottom_categories_progress)
        val bottomMasterRecycler = view.findViewById<RecyclerView>(R.id.bottomMasterRecycler)
        var callMaster: Call<Products?>? =
            APIInterface.createLocal()
                .getMasterByGender("getMasterByGender", intent.getStringExtra("category"))
        callMaster!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                try {
                    progressBar.visibility = View.GONE
                    var masterDataList: ArrayList<ProductDatum> =
                        response.body()!!.getProductData() as ArrayList<ProductDatum>
                    Log.e("MasterDataList", masterDataList.size.toString())
                    var bottomMasterAdapter =
                        BottomMasterAdapter(
                            this@CategoriesActivity,
                            masterDataList,
                            intent.getStringExtra("category").toString()
                        )
                    bottomMasterRecycler.isNestedScrollingEnabled = false
                    bottomMasterRecycler.layoutManager =
                        LinearLayoutManager(this@CategoriesActivity)
                    bottomMasterRecycler.adapter = bottomMasterAdapter
                } catch (e: Exception) {

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {

            }

        })
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

}