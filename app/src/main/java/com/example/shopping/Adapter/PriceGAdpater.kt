package com.example.shopping.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.R
import com.squareup.picasso.Picasso

class PriceGAdpater(
    private val productsArrayList: List<ProductDatum>,
    private val context: Context
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    lateinit var imageView: ImageView
    lateinit var name: TextView
    lateinit var price: EditText
    lateinit var discount: EditText
    lateinit var total: TextView
    lateinit var done: Button

    override fun getCount(): Int {
        return productsArrayList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }


    override fun getItemId(position: Int): Long {
        return 0
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.update_price_layout, null)
        }

        imageView = convertView!!.findViewById(R.id.image)
        name = convertView!!.findViewById(R.id.name)
        price = convertView!!.findViewById(R.id.total_price)
        discount = convertView!!.findViewById(R.id.total_discount)
        total = convertView!!.findViewById(R.id.discounted_price)
        done = convertView!!.findViewById(R.id.done_btn)

        var productDatum = productsArrayList[position]
        Picasso.get().load(APIInterface.IMAGE_URL + "images/" + productDatum.getId() + ".jpg").centerCrop().resize(150,200)
            .into(imageView);
        if (position == 0) {
            Log.e("ImageURL", APIInterface.IMAGE_URL + "images/" + productDatum.getId() + ".jpg")
        }
        name.text = productDatum.getProductDisplayName()
        price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textChange()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        discount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textChange()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        done.setOnClickListener {
            var str: String = total.text.toString()
            var total: Float = str.toFloat()
            if (total <= 0) {
                Toast.makeText(
                    context,
                    "Error",
                    Toast.LENGTH_LONG
                ).show()
            } else {

            }
        }
        
        return convertView
    }

    private fun textChange() {
        try {
            var totalValue: Float
            var price: String = price.text.toString()
            var discount: String = discount.text.toString()
            totalValue = price.toFloat() - discount.toFloat()
            total.text = totalValue.toString()
        } catch (e: Exception) {

        }
    }

}