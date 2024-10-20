package com.example.esemkastore.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.Adapter.HomeAdapter
import com.example.esemkastore.Helper.Core
import com.example.esemkastore.Helper.HttpHelper
import com.example.esemkastore.MainActivity
import com.example.esemkastore.Model.Item
import com.example.esemkastore.R
import org.json.JSONArray

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        with(view)
        {
            var lblWelcome: TextView = findViewById(R.id.lblWelcome)
            var itemRv: RecyclerView = findViewById(R.id.itemRv)

            lblWelcome.setText("Welcome ${Core.user.name}")

            var data = JSONArray(HttpHelper("Home/Item", "GET").execute().get())
            var item = mutableListOf<Item>()
            for (i in 0 until data.length())
            {
                var x = data.getJSONObject(i)
                item.add(Item(x.getInt("id"), x.getString("name"), x.getString("description"), x.getInt("price"), x.getInt("stock")))
            }

            itemRv.adapter = HomeAdapter(item, activity as MainActivity)
        }
        return view
    }

}