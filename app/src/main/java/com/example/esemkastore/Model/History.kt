package com.example.esemkastore.Model

data class History(val totalPrice: Int, val orderDate: String, val acceptanceDate: String, val detail: MutableList<History_Detail>)
