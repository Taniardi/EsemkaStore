package com.example.esemkastore.Adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.esemkastore.R
import org.json.JSONArray
import androidx.core.content.ContextCompat
import com.example.esemkastore.Fragment.CartFragment
import com.example.esemkastore.Model.Service
import org.json.JSONObject

class SpinnerAdapter(var list: MutableList<Service>, val frag: CartFragment): BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.spinner_item, parent, false)
        with(view){
            val lbLTitle: TextView = findViewById(R.id.lblTitle)
            val lblDescripption: TextView = findViewById(R.id.lblDescription)
            var service = getItem(position) as Service
            lbLTitle.setText(service.name)
            lblDescripption.setText("Rp. ${service.price}.00 ( ${service.duration} day(s) )")
            frag.refreshTotalPrice()
        }
        return view
    }
}