package com.example.esemkastore.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.Adapter.HistoryAdapter
import com.example.esemkastore.Helper.Core
import com.example.esemkastore.Helper.HttpHelper
import com.example.esemkastore.Model.History
import com.example.esemkastore.Model.History_Detail
import com.example.esemkastore.Model.Item
import com.example.esemkastore.R
import org.json.JSONArray

class HistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_history, container, false)
        with(view)
        {
            val rvHistory: RecyclerView = findViewById(R.id.rvHistory)
            var checkData = HttpHelper("History/Transaction/${Core.user.id}","GET").execute().get()
            if(checkData == null)
            {
                return view
            }
            val rawdata = JSONArray(checkData)
            val data = mutableListOf<History>()
            (0 until rawdata.length()).map {
                val currentData  = rawdata.getJSONObject(it)
                val detail =  currentData.getJSONArray("detail")
                val detailData = mutableListOf<History_Detail>()
                (0 until detail.length()).map {
                    val currentDetail = detail.getJSONObject(it)
                    val currentItem = currentDetail.getJSONObject("item")
                    detailData.add(History_Detail(Item(currentItem.getInt("id"), currentItem.getString("name"), currentItem.getString("description"), currentItem.getInt("price"), currentItem.getInt("stock")), currentDetail.getInt("count")))
                }
                data.add(History(currentData.getInt("totalPrice"), currentData.getString("orderDate"), currentData.getString("acceptanceDate"), detailData))
            }
            rvHistory.adapter = HistoryAdapter(data)
        }
        return view
    }
}