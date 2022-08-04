package com.example.shopping.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import com.squareup.picasso.Picasso
import retrofit2.Call

class MasterHomeSubAdapter(
    private var context: Context,
    productsArrayList: ArrayList<ProductDatum>,
    masterCategory: String,
) :
    RecyclerView.Adapter<MasterHomeSubAdapter.ViewHolder>() {

    var productsArrayList: ArrayList<ProductDatum>
    lateinit var masterCategory: String

    init {
        this.context = context
        this.productsArrayList = productsArrayList
        this.masterCategory = masterCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context)
                .inflate(R.layout.master_category_sub_recycler, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.masterHomeImage)
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        Log.e("ArrayListSizeSub", productsArrayList[position].getArticleType().toString())
        Picasso.get()
            .load(APIInterface.LOCAL_URL + "master_images/home_master/" + masterCategory + "/" + productDatum.getArticleType() + ".jpg")
            .into(holder.imageView)
        holder.imageView.setOnClickListener {
            Log.e(
                "ImageUrl",
                APIInterface.LOCAL_URL + "master_images/home_master/" + masterCategory + "/" + productDatum.getArticleType() + ".jpg"
            )
        }
    }

}