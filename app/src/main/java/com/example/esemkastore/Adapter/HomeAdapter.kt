package com.example.esemkastore.Adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.Fragment.ItemDetailFragment
import com.example.esemkastore.Helper.HttpHelperBitmap
import com.example.esemkastore.MainActivity
import com.example.esemkastore.Model.Item
import com.example.esemkastore.R

class HomeAdapter(var list: MutableList<Item>, var ctx: MainActivity): RecyclerView.Adapter<HomeAdapter.VH>(){
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(response: Item)
        {
            with(itemView)
            {
                var lblName: TextView = findViewById(R.id.lblName)
                var lblDescription: TextView = findViewById(R.id.lblDescription)
                var lblPrice: TextView = findViewById(R.id.lblPrice)
                var imgView: ImageView = findViewById(R.id.img)

                var bitmap: Bitmap? = HttpHelperBitmap("Home/Item/Photo/${response.id}").execute().get()
                imgView.setImageBitmap(bitmap ?: resources.getDrawable(R.drawable.img).toBitmap())

                lblName.setText(response.name)
                lblDescription.setText(if(response.description.length > 40) response.description.take(36) + " ..." else response.description)
                lblPrice.setText("Rp. ${response.price}.00")

                itemView.setOnClickListener {
                    ctx.setFrag(ItemDetailFragment(response), response.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list.get(position))
    }

    override fun getItemCount(): Int = list.size
}