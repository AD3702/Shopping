package com.example.shopping.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Adapter.*
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var productArrayList: ArrayList<ProductDatum>
    lateinit var recyclerView: RecyclerView

    private lateinit var masterRecycler: RecyclerView

    //    lateinit var masterGrid: GridView
    lateinit var progressBar: ProgressBar
    lateinit var genderArrayList: ArrayList<ProductDatum>
    lateinit var masterArrayList: ArrayList<ProductDatum>
    lateinit var topRowArrayList: ArrayList<ProductDatum>
    lateinit var imageList: ArrayList<ProductDatum>
    private lateinit var sliderView: SliderView
    lateinit var forHimHerImageMain1: ImageView
    lateinit var forHimHerImageMain2: ImageView
    lateinit var wishlist: ImageView
    lateinit var tempList: ArrayList<ProductDatum>
    lateinit var apparelArrayList: ArrayList<ProductDatum>
    lateinit var apparelArrayListTemp: ArrayList<ProductDatum>
    lateinit var nestedScrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        productArrayList = ArrayList()
        tempList = ArrayList()
        recyclerView = findViewById(R.id.mainrecycler)
        progressBar = findViewById(R.id.progress_bar)
        masterRecycler = findViewById(R.id.masterRecycler)
//        masterGrid = findViewById(R.id.masterGrid)
        forHimHerImageMain1 = findViewById(R.id.for_him_her_image_1)
        forHimHerImageMain2 = findViewById(R.id.for_him_her_image_2)
        sliderView = findViewById(R.id.imageSlider)
        nestedScrollView = findViewById(R.id.nestedScrollViewMain)
        nestedScrollView.visibility = View.INVISIBLE
        topRowArrayList = ArrayList()
        setMasterImage()

        wishlist = findViewById(R.id.wishlist_main)
        wishlist.setOnClickListener {
            startActivity(Intent(this@MainActivity, WishListActivity::class.java))
        }

        var callGender: Call<Products?>? =
            APIInterface.createLocal().getDistinctMasterData("getDistinctGenderData")
        callGender!!.enqueue(object : Callback<Products?> {
            override fun onResponse(
                call: Call<Products?>,
                response: Response<Products?>
            ) {
                try {
                    genderArrayList =
                        response.body()!!.getProductData() as ArrayList<ProductDatum>
                    var loop: Int = 0
                    while (loop < genderArrayList.size) {
                        if (genderArrayList[loop].getGender()
                                .equals("Men") || genderArrayList[loop].getGender()
                                .equals("Women") || genderArrayList[loop].getGender()
                                .equals("Boys") || genderArrayList[loop].getGender()
                                .equals("Girls")
                        ) {
                            topRowArrayList.add(genderArrayList[loop])
                        }
                        loop++
                    }
                    var callMaster: Call<Products?>? =
                        APIInterface.createLocal().getDistinctMasterData("getDistinctMasterData")
                    callMaster!!.enqueue(object : Callback<Products?> {
                        override fun onResponse(
                            call: Call<Products?>,
                            response: Response<Products?>
                        ) {
                            try {
                                masterArrayList =
                                    response.body()!!.getProductData() as ArrayList<ProductDatum>
                                setMasterRecycler(masterArrayList)
                                var loop: Int = 0
                                while (loop < masterArrayList.size) {
                                    if (masterArrayList[loop].getMasterCategory()
                                            .equals("Accessories") || masterArrayList[loop].getMasterCategory()
                                            .equals("Footwear")
                                    ) {
                                        topRowArrayList.add(masterArrayList[loop])
                                    }
                                    loop++
                                }
                                recyclerView.layoutManager =
                                    LinearLayoutManager(
                                        this@MainActivity,
                                        RecyclerView.HORIZONTAL,
                                        false
                                    )
                                var adapter = TopRowAdapter(this@MainActivity, topRowArrayList)
                                recyclerView.adapter = adapter
                                Log.e("ArrayListSize", topRowArrayList.size.toString())
                            } catch (e: Exception) {
                                Log.e("ArrayListSize", e.toString())
                            }

                        }

                        override fun onFailure(call: Call<Products?>, t: Throwable) {
                            progressBar.visibility = View.GONE
                            Log.e("OnFailed", t.toString())
                        }

                    })
                } catch (e: Exception) {
                    Log.e("ArrayListSize", e.toString())
                }

            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("OnFailed", t.toString())
            }

        })

        var callImages: Call<Products?>? =
            APIInterface.createLocal().getSliderImages("getImages", "home")
        callImages!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                imageList = response.body()!!.getProductData() as ArrayList<ProductDatum>
                setSlider(imageList)
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {

            }

        })

    }

    private fun setMasterImage() {
        Picasso.get()
            .load(APIInterface.LOCAL_URL + "slider_images/for_him_her/01.png")
            .into(forHimHerImageMain1)
        Picasso.get()
            .load(APIInterface.LOCAL_URL + "slider_images/for_him_her/02.png")
            .into(forHimHerImageMain2)
        var intent = Intent(this@MainActivity, CategoriesActivity::class.java)
        forHimHerImageMain1.setOnClickListener {
            intent.putExtra("category", "Men")
            startActivity(intent)
        }
        forHimHerImageMain2.setOnClickListener {
            intent.putExtra("category", "Women")
            startActivity(intent)
        }
    }

    fun setSlider(imageList: ArrayList<ProductDatum>) {
        val sliderAdapterExample = SliderAdapterHomeBanner(applicationContext)

        var i: Int = 0
        while (i < imageList.size) {
            var productDatum = imageList[i]
            var sliderItem = SliderItem()
            sliderItem.setImageUrl(APIInterface.LOCAL_URL + productDatum.getBannerImageUrl())
            sliderItem.setBannerGender(productDatum.getBannerGender())
            sliderItem.setBannerArticleType(productDatum.getBannerArticleType())
            sliderAdapterExample.addItem(sliderItem)
            Log.e(
                "ImageURL",
                APIInterface.LOCAL_URL + productDatum.getBannerImageUrl()
            )
            i++
        }
        sliderView.setSliderAdapter(sliderAdapterExample)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.indicatorSelectedColor = Color.GRAY
        sliderView.indicatorUnselectedColor = Color.WHITE
        sliderView.indicatorRadius = 2
        sliderView.scrollTimeInSec = 4
        sliderView.startAutoCycle()
    }

    fun setMasterRecycler(arrayList: ArrayList<ProductDatum>) {
        var i: Int = 0
        while (i < arrayList.size) {
            if (arrayList[i].getMasterCategory()
                    .equals("Apparel") || arrayList[i].getMasterCategory()
                    .equals("Accessories") || arrayList[i].getMasterCategory()
                    .equals("Footwear")
            ) {
                tempList.add(arrayList[i])
            }
            i++
        }
        apparelArrayListTemp = ArrayList()
        getTempList(0, tempList)
    }

    private fun getTempList(position: Int, productsArrayList: ArrayList<ProductDatum>) {
        var productDatum = productsArrayList[position]
        var masterCategory: String = productDatum.getMasterCategory().toString()
        var loop = 0
        var call: Call<Products?>? = APIInterface.createLocal()
            .getArticleFromMaster(
                "getArticleFromMaster",
                masterCategory
            )
        call!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                apparelArrayList = response.body()!!.getProductData() as ArrayList<ProductDatum>
                while (loop < apparelArrayList.size) {
                    var productDatum1 = apparelArrayList[loop]
                    if (productDatum1.getArticleType()
                            .equals("Tshirts") || productDatum1.getArticleType()
                            .equals("Shirts") || productDatum1.getArticleType()
                            .equals("Jeans") || productDatum1.getArticleType()
                            .equals("Tops") || productDatum1.getArticleType()
                            .equals("Belts") || productDatum1.getArticleType()
                            .equals("Handbags") /*|| productDatum1.getArticleType()
                            .equals("Formal Shoes") || productDatum1.getArticleType()
                            .equals("Sandals")*/
                    ) {
                        var data = ProductDatum()
                        data.setMasterCategory(masterCategory)
                        data.setArticleType(apparelArrayList[loop].getArticleType())
                        apparelArrayListTemp.add(data)
                    } else {

                    }
                    loop++
                }
                if (position == 0) {
                    getTempList(1, tempList)
                } else if (position == 1) {
                    getTempListGrid(2, tempList)
                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {
                Log.e("TestData", t.toString())
            }

        })
    }

    private fun getTempListGrid(position: Int, productsArrayList: ArrayList<ProductDatum>) {
        var productDatum = productsArrayList[position]
        var masterCategory: String = productDatum.getMasterCategory().toString()
        var loop = 0
        var call: Call<Products?>? = APIInterface.createLocal()
            .getArticleFromMaster(
                "getArticleFromMaster",
                masterCategory
            )
        call!!.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                apparelArrayList = response.body()!!.getProductData() as ArrayList<ProductDatum>
                while (loop < apparelArrayList.size) {
                    var productDatum1 = apparelArrayList[loop]
                    if (productDatum1.getArticleType()
                            .equals("Tshirts") || productDatum1.getArticleType()
                            .equals("Shirts") || productDatum1.getArticleType()
                            .equals("Jeans") || productDatum1.getArticleType()
                            .equals("Tops") || productDatum1.getArticleType()
                            .equals("Belts") || productDatum1.getArticleType()
                            .equals("Handbags") /*productDatum1.getArticleType()
                            .equals("Formal Shoes") || productDatum1.getArticleType()
                            .equals("Sandals")*/
                    ) {
                        var data = ProductDatum()
                        data.setMasterCategory(masterCategory)
                        data.setArticleType(apparelArrayList[loop].getArticleType())
                        apparelArrayListTemp.add(data)
                    } else {

                    }
                    loop++
                }
                Log.e("TestData", apparelArrayListTemp.size.toString())
                loop = 0
                while (loop < apparelArrayListTemp.size) {
                    Log.e("TestData", apparelArrayListTemp[loop].getArticleType().toString())
                    loop++
                }
                var masterHomeAdapter =
                    MasterHomeAdapter(this@MainActivity, apparelArrayListTemp)
                masterRecycler.layoutManager = GridLayoutManager(this@MainActivity, 3)
                masterRecycler.adapter = masterHomeAdapter
                masterRecycler.isNestedScrollingEnabled = false
                nestedScrollView.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {
                Log.e("TestData", t.toString())
            }

        })
    }

}