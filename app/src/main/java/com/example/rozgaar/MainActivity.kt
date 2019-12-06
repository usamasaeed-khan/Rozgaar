package com.example.rozgaar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import android.os.Build
import android.widget.Button
import android.widget.Toast
import okhttp3.OkHttpClient
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn:Button=findViewById(R.id.search_btn)
        btn.setOnClickListener {
            try {
                startActivity(Intent(this,NotifictionActivity::class.java))
                finish()

            }
            catch (e:Exception){
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }

        }
    }
}
