package com.example.shopping.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.Database.APIInterface
import com.example.shopping.Database.ProductDatum
import com.example.shopping.R
import com.squareup.picasso.Picasso

class PriceAdapter(private var context: Context, productsArrayList: ArrayList<ProductDatum>) :
    RecyclerView.Adapter<PriceAdapter.ViewHolder>() {

    lateinit var productsArrayList: ArrayList<ProductDatum>

    init {
        this.context = context
        this.productsArrayList = productsArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.update_price_layout, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var imageView: ImageView
        lateinit var name: TextView
        lateinit var price: EditText
        lateinit var discount: EditText
        lateinit var total: TextView
        lateinit var done: Button

        init {
            imageView = itemView.findViewById(R.id.image)
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.total_price)
            discount = itemView.findViewById(R.id.total_discount)
            total = itemView.findViewById(R.id.discounted_price)
            done = itemView.findViewById(R.id.done_btn)
        }
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productDatum = productsArrayList[position]
        Picasso.get().load(APIInterface.IMAGE_URL + "images/" + productDatum.getId() + ".jpg")
            .into(holder.imageView);
        if (position == 0) {
            Log.e("ImageURL", APIInterface.IMAGE_URL + "images/" + productDatum.getId() + ".jpg")
        }
        holder.name.text = productDatum.getProductDisplayName()
        holder.price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textChange(holder)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        holder.discount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textChange(holder)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        holder.done.setOnClickListener {
            var str: String = holder.total.text.toString()
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

    }

    private fun textChange(holder: ViewHolder) {
        try {
            var totalValue: Float
            var price: String = holder.price.text.toString()
            var discount: String = holder.discount.text.toString()
            totalValue = price.toFloat() - discount.toFloat()
            holder.total.text = totalValue.toString()
        } catch (e: Exception) {

        }
    }

}