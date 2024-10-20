package com.example.esemkastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.esemkastore.Fragment.BlankFragment
import com.example.esemkastore.Fragment.CartFragment
import com.example.esemkastore.Fragment.HistoryFragment
import com.example.esemkastore.Fragment.HomeFragment
import com.example.esemkastore.Helper.Core
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MainActivity : AppCompatActivity() {
    private  lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabLayout)
        setFrag(HomeFragment(), "Home")
        tabLayout.setOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position)
                {
                    0 ->{
                        setFrag(HomeFragment(), "Home")
                    }
                    1 -> {
                        if(Core.cart.size == 0 )
                        {
                            setFrag(BlankFragment(), "Cart")
                        }else{
                            setFrag(CartFragment(), "Cart")
                        }
                    }
                    2 -> {
                        setFrag(HistoryFragment(), "History")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    fun setFrag(frag: Fragment, title: String)
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, frag).commit()
        supportActionBar?.title = title
        refreshCart();
    }

    fun refreshCart()
    {
        tabLayout.getTabAt(1)?.setText("Cart(${Core.cart.size})")
    }

    fun setSelectedTab(position: Int)
    {
        tabLayout.getTabAt(position)?.select()
    }
}