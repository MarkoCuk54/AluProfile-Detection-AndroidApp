package com.example.emerus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emerus.R
import android.content.Intent
import android.view.Window
import android.widget.Button
import com.example.emerus.Contact

class Support : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val decorView = window.decorView
        setContentView(R.layout.activity_support)
        val contactbtn = findViewById<Button>(R.id.contactbtn)
        contactbtn.setOnClickListener { startActivity(Intent(this@Support, Contact::class.java)) }
    }
}