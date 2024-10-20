package com.example.esemkastore.Helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class HttpHelperBitmap(url: String): AsyncTask<Void, Void, Bitmap?>() {
    var connection = URL("http://10.0.2.2:5000/api/" + url).openConnection() as HttpURLConnection
    lateinit var body: String

    init {
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")
    }

    override fun doInBackground(vararg p0: Void?): Bitmap? {
        if(::body.isInitialized)
        {
            var writter = DataOutputStream(connection.outputStream)
            writter.writeBytes(body)
            writter.close()
        }
        try {
            var result = BitmapFactory.decodeStream(connection.inputStream)
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