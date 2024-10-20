package com.example.esemkastore.Fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.example.esemkastore.Helper.Core
import com.example.esemkastore.Helper.HttpHelperBitmap
import com.example.esemkastore.MainActivity
import com.example.esemkastore.Model.Cart
import com.example.esemkastore.Model.Item
import com.example.esemkastore.R

class ItemDetailFragment(var item: Item) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)
        with(view){
            val name: TextView = findViewById(R.id.lblName)
            val description: TextView = findViewById(R.id.lblDescription)
            val price: TextView = findViewById(R.id.lblPrice)
            val lblTotalprice: TextView = findViewById(R.id.lblTotalPrice)
            val stock: TextView = findViewById(R.id.lblStock)
            val imgView: ImageView = findViewById(R.id.imgView)
            val btnAdd: Button = findViewById(R.id.btnAdd)
            val btnMinus: Button = findViewById(R.id.btnMinus)
            val btnAddToCart: Button = findViewById(R.id.btnAddToCart)
            val inputCount: TextView = findViewById(R.id.txtCount)
            val stockCount = item.stock - Core.cart.filter { it.item == item }.sumOf { it.count }

            val refreshToTalPrice= {
                val count = inputCount.text.toString().toInt()
                lblTotalprice.setText("Total Price : Rp. ${item.price * count},00")
            }
            refreshToTalPrice()

            name.setText(item.name)
            description.setText(item.description)
            price.setText("Rp. ${item.price}.00")
            stock.setText("Stock : ${stockCount}")
            btnAddToCart.isEnabled = !(stockCount == 0)

            var bitmap: Bitmap? = HttpHelperBitmap("Home/Item/Photo/${item.id}").execute().get()
            imgView.setImageBitmap(bitmap ?: resources.getDrawable(R.drawable.img).toBitmap())

            val setNumber = { num:Int ->
                inputCount.setText(num.toString())
            }

            btnAdd.setOnClickListener{
                setNumber(inputCount.text.toString().toInt() + 1)
                refreshToTalPrice()
            }

            btnMinus.setOnClickListener {
                val result = inputCount.text.toString().toInt() - 1
                if (result > 0) setNumber(result)
                refreshToTalPrice()
            }

            btnAddToCart.setOnClickListener{
                val count = inputCount.text.toString().toInt()
                if(count > stockCount)
                {
                    Toast.makeText(this.context, "cannot but more than stock!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val existingCartItem = Core.cart.find { it.item == item }
                if (existingCartItem != null) {
                    existingCartItem.count += count
                } else {
                    Core.cart.add(Cart(item, count))
                }

                (activity as MainActivity).setFrag(HomeFragment(), "Home")
                Log.d("learn", "${Core.cart} ")
            }

         }
        return view
    }
}