package com.example.shopping.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestingActivity : AppCompatActivity() {

    lateinit var testList: ArrayList<ProductDatum>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
        testList = ArrayList()
        val call: Call<Products?>? =
            APIInterface.createLocal().getCount("getCountOfMasterCategory", "Personal Care")
        call!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                testList = response.body()!!.getProductData() as ArrayList<ProductDatum>
                var loop: Int = 0
                while (loop < testList.size) {
                    Log.e(
                        "Count",
                        testList[loop].getArticleType() + ": " + testList[loop].getCOUNTId()
                    )
                    loop++
                }
                Log.e(
                    "Count",
                    "List Total: $loop"
                )
                loop = 0
                var temp: Int = 0
                while (loop < testList.size) {
                    if (testList[loop].getCOUNTId()!!.toInt() > 50) {
                        Log.e(
                            "Count",
                            "Top List: " + testList[loop].getArticleType() + ": " + testList[loop].getCOUNTId()
                        )
                        temp++
                    }
                    loop++
                }
                Log.e(
                    "Count",
                    "Top List Total: $temp"
                )
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {

            }

        })
    }
}