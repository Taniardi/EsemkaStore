package com.example.esemkastore.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.Helper.HttpHelper
import com.example.esemkastore.Model.History
import com.example.esemkastore.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryAdapter(val list: MutableList<History>): RecyclerView.Adapter<HistoryAdapter.VH>(){
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(response: History)
        {
            with(itemView)
            {
                val lblItemCartHeader: TextView = findViewById(R.id.txtItemCartHeader)
                val rvHistory: RecyclerView = findViewById(R.id.rvHistoryItem)

                val parser = DateTimeFormatter.ISO_LOCAL_DATE_TIME
                val orderDate = LocalDateTime.parse(response.orderDate, parser)
                var formatter =  DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
                lblItemCartHeader.text = orderDate.format(formatter)

                rvHistory.adapter = HistoryDetailAdapter(response.detail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}