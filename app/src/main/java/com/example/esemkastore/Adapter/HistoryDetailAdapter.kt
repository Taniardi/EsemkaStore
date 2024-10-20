package com.example.esemkastore.Adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.Helper.HttpHelperBitmap
import com.example.esemkastore.Model.History_Detail
import com.example.esemkastore.R

class HistoryDetailAdapter(var list: MutableList<History_Detail>): RecyclerView.Adapter<HistoryDetailAdapter.VH>(){
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(response: History_Detail){
            with(itemView){
                val imgView: ImageView = findViewById(R.id.historyImg)
                val lblTitle: TextView = findViewById(R.id.lblTitle)
                val lblCount: TextView = findViewById(R.id.lblCount)
                val lblPrice: TextView = findViewById(R.id.lblPrice)

                var bitmap: Bitmap? = HttpHelperBitmap("Home/Item/Photo/${response.item.id}").execute().get()
                imgView.setImageBitmap(bitmap ?: resources.getDrawable(R.drawable.img).toBitmap())

                lblTitle.setText(response.item.name)
                lblCount.setText("Count : " + response.count)
                lblPrice.setText("Price: Rp. ${response.item.price * response.count},00")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_detail, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}