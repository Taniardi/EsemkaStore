package com.example.esemkastore.Adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.Fragment.CartFragment
import com.example.esemkastore.Helper.Core
import com.example.esemkastore.Helper.HttpHelperBitmap
import com.example.esemkastore.MainActivity
import com.example.esemkastore.Model.Cart
import com.example.esemkastore.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CartAdapter(val ctx: MainActivity, val frag: CartFragment): RecyclerView.Adapter<CartAdapter.VH>(){
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(response: Cart)
        {
            with(itemView)
            {
                val imgView: ImageView = findViewById(R.id.cartImg)
                val lblTitle: TextView = findViewById(R.id.lblTitle)
                val lblCount: TextView = findViewById(R.id.lblCount)
                val lblPrice: TextView = findViewById(R.id.lblPrice)
                val btnDelete: FloatingActionButton = findViewById(R.id.btnDelete)

                var bitmap: Bitmap? = HttpHelperBitmap("Home/Item/Photo/${response.item.id}").execute().get()
                imgView.setImageBitmap(bitmap ?: resources.getDrawable(R.drawable.img).toBitmap())
                lblTitle.setText(response.item.name)
                lblCount.setText("Count : " + response.count)
                lblPrice.setText("Price: ${response.item.price * response.count}")

                btnDelete.setOnClickListener{
                    Core.cart.remove(response)
                    notifyDataSetChanged()
                    ctx.refreshCart()
                    frag.refreshTotalPrice()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(Core.cart[position])
    }

    override fun getItemCount(): Int = Core.cart.size
}