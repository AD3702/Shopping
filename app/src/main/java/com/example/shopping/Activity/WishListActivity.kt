package com.example.shopping.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Adapter.ProductDetailsWishListAdapter
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WishListActivity : AppCompatActivity() {

    companion object {
        lateinit var wishRecyclerView: RecyclerView
        lateinit var wishListArrayList: ArrayList<ProductDatum>
        lateinit var backWishList: ImageView
        lateinit var wishListActivity: WishListActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)
        wishRecyclerView = findViewById(R.id.wishList_recycler)
        backWishList = findViewById(R.id.back_wishlist)
        wishListActivity = this@WishListActivity
        backWishList.setOnClickListener {
            try {
                if (intent.getStringExtra("intent_from").equals("prod_details")) {
                    var intentget = Intent(wishListActivity, ProductDetails::class.java)
                    intentget.putExtra("gender", intent.getStringExtra("gender").toString())
                    intentget.putExtra(
                        "articleType",
                        intent.getStringExtra("articleType").toString()
                    )
                    intentget.putExtra("imageType", intent.getStringExtra("imageType").toString())
                    startActivity(intentget)
                    finish()
                } else {
                    onBackPressed()
                }
            } catch (e: Exception) {
                Log.e(
                    "getWishList",
                    e.toString()
                )
                finish()
            }
        }
        var callList: Call<Products?>? =
            APIInterface.createLocal().getWishList("getWishListDesc", "1")
        callList!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                try {
                    wishListArrayList =
                        response.body()!!.getProductData() as ArrayList<ProductDatum>
                    var productDetailsAdapter =
                        ProductDetailsWishListAdapter(
                            this@WishListActivity,
                            wishListArrayList
                        )
                    wishRecyclerView.layoutManager =
                        LinearLayoutManager(this@WishListActivity)
                    wishRecyclerView.adapter = productDetailsAdapter
                } catch (e: Exception) {

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}