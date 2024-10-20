package com.example.esemkastore.Helper

import com.example.esemkastore.Model.Cart
import com.example.esemkastore.Model.Item
import com.example.esemkastore.Model.User
import org.json.JSONObject

class Core {
    companion object{
        lateinit var user: User
        var cart: MutableList<Cart> = mutableListOf()
    }
}