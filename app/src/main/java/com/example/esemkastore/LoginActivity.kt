package com.example.esemkastore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.esemkastore.Helper.Core
import com.example.esemkastore.Helper.HttpHelper
import com.example.esemkastore.Model.User
import com.google.android.material.button.MaterialButton
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var txtEmail: EditText = findViewById(R.id.txtEmail)
        var txtPassword: EditText = findViewById(R.id.txtPassword)
        var btnLogin: MaterialButton = findViewById(R.id.btnLogin)

        txtEmail.setText("khuddle0@cbc.ca")
        txtPassword.setText("P@ssw0rd123")

        btnLogin.setOnClickListener {
            var transfer = JSONObject().apply {
                put("email", txtEmail.text)
                put("password", txtPassword.text)
            }

            var result = HttpHelper("Login", "POST").setBody(transfer.toString()).execute().get()

            if(result == null)
            {
                Toast.makeText(this, "email or password wrong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var x = JSONObject(result)
            Core.user = User(x.getInt("id"), x.getString("email"), x.getString("name"), x.getString("birthday"), x.getString("phoneNumber"))
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}