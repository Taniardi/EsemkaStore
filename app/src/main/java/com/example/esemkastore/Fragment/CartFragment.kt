package com.example.esemkastore.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.Adapter.CartAdapter
import com.example.esemkastore.Adapter.SpinnerAdapter
import com.example.esemkastore.Helper.Core
import com.example.esemkastore.Helper.HttpHelper
import com.example.esemkastore.MainActivity
import com.example.esemkastore.Model.Item
import com.example.esemkastore.Model.Service
import com.example.esemkastore.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar

class CartFragment : Fragment() {
    var serviceData = mutableListOf<Service>()
    lateinit var spinner: Spinner
    lateinit var lblTotalPrice: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        with(view) {
            val cartRv: RecyclerView = findViewById(R.id.cartRv)
            spinner = findViewById(R.id.spinnerService)
            lblTotalPrice = findViewById(R.id.lblTotalPrice)
            val btnCheckout: Button = findViewById(R.id.btnCheckOut)

            val rawdata = JSONArray(HttpHelper("Checkout/Service", "GET").execute().get())
            (0 until rawdata.length()).map {
                val obj = rawdata.getJSONObject(it)
                serviceData.add(Service(obj.getInt("id"), obj.getString("name"), obj.getInt("duration"), obj.getInt("price")))
            }
            spinner.adapter = SpinnerAdapter(serviceData, this@CartFragment)
            cartRv.adapter = CartAdapter(activity as MainActivity, this@CartFragment)

            refreshTotalPrice()

            btnCheckout.setOnClickListener{
                val detail = JSONArray().apply {
                    Core.cart.forEach { item ->
                        put(JSONObject().apply {
                            put("itemId", item.item.id)
                            put("count", item.count)
                        })
                    }
                }
                val format = SimpleDateFormat("yyyy-MM-dd")
                val c = Calendar.getInstance()
                val orderDate = format.format(c.time)
                c.add(Calendar.DAY_OF_MONTH, serviceData[spinner.selectedItemPosition].duration)
                val acceptanceDate = format.format(c.time)
                val transfer = JSONObject().apply {
                    put("userId", Core.user.id)
                    put("serviceId", serviceData[spinner.selectedItemPosition].id)
                    put("totalPrice", Core.cart.sumOf { it.count * it.item.price } + serviceData[spinner.selectedItemPosition].price)
                    put("orderDate", orderDate)
                    put("acceptanceDate", acceptanceDate)
                    put("detail", detail)
                }

                HttpHelper("Checkout/Transaction", "POST").setBody(transfer.toString()).execute().get()
                Core.cart.clear()
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).setFrag(HomeFragment(), "Home")
                (activity as MainActivity).setSelectedTab(0)
            }

        }
        return view
    }
    val refreshTotalPrice = {
        var total = Core.cart.sumOf { it.item.price * it.count }
        var servicePrice = serviceData[spinner.selectedItemPosition].price
        lblTotalPrice.setText("Total Price : Rp. ${total + servicePrice},00")
    }

}