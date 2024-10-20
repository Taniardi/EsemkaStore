package com.example.esemkastore.Helper

import android.os.AsyncTask
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class HttpHelper(url: String, method: String): AsyncTask<Void, Void, String?>() {
    var connection = URL("http://10.0.2.2:5000/api/" + url).openConnection() as HttpURLConnection
    lateinit var body: String

    init {
        connection.requestMethod = method.uppercase()
        connection.setRequestProperty("Content-Type", "application/json")
    }

    fun setBody(value: String): HttpHelper {
        body = value
        return this
    }

    override fun doInBackground(vararg p0: Void?): String? {
        if(::body.isInitialized)
        {
            var writter = DataOutputStream(connection.outputStream)
            writter.writeBytes(body)
            writter.close()
        }
        try {
            var result = connection.inputStream.bufferedReader().use { it.readText() }
            connection.inputStream.close()
            connection.disconnect()
            return result
        }
        catch (r: Exception)
        {
            return null
        }
    }
}