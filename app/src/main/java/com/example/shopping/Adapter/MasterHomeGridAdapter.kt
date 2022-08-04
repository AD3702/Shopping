package com.example.shopping.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.getSystemService
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.Database.Products
import com.example.shopping.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MasterHomeGridAdapter(
    // on below line we are creating two
    // variables for course list and context
    private val productArrayList: List<ProductDatum>,
    private val context: Context
) :
    BaseAdapter() {
    // in base adapter class we are creating variables
    // for layout inflater, course image view and course text view.
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView

    // below method is use to return the count of course list
    override fun getCount(): Int {
        return productArrayList.size
    }

    // below function is use to return the item of grid view.
    override fun getItem(position: Int): Any? {
        return null
    }

    // below function is use to return item id of grid view.
    override fun getItemId(position: Int): Long {
        return 0
    }

    // in below function we are getting individual item of grid view.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
//
//        if (layoutInflater == null) {
//            layoutInflater =
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertView == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertView = layoutInflater!!.inflate(R.layout.master_category_sub_recycler, null)
        }

        // on below line we are initializing our course image view
        // and course text view with their ids.
        imageView = convertView!!.findViewById(R.id.masterHomeImage)
        // on below line we are setting image for our course image view.

        // at last we are returning our convert view.

        var productDatum = productArrayList[position]

        Picasso.get()
            .load(APIInterface.LOCAL_URL + "master_images/home_master/" + productDatum.getMasterCategory() + "/" + productDatum.getArticleType() + ".jpg")
            .into(imageView)

        return convertView

    }
}
