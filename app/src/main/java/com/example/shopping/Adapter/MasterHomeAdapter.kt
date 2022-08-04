package com.example.shopping.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Activity.ProductDetails
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.R
import com.squareup.picasso.Picasso


class MasterHomeAdapter(private var context: Context, productsArrayList: ArrayList<ProductDatum>) :
    RecyclerView.Adapter<MasterHomeAdapter.ViewHolder>() {

    var productsArrayList: ArrayList<ProductDatum>


    init {
        this.context = context
        this.productsArrayList = productsArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context)
                .inflate(R.layout.master_category_sub_recycler, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainCategory: ImageView
        val linearLayout: LinearLayout

        init {
            mainCategory = itemView.findViewById(R.id.masterHomeImage)
            linearLayout = itemView.findViewById(R.id.linearLayout)
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        Picasso.get()
            .load(APIInterface.LOCAL_URL + "master_images/home_master/" + productDatum.getMasterCategory() + "/" + productDatum.getArticleType() + ".jpg")
            .into(holder.mainCategory)

        holder.linearLayout.setOnClickListener {
            var intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("articleType", productDatum.getArticleType())
            intent.putExtra("imageType", "HomeGrid")
            context.startActivity(intent)
        }

    }
}