package com.example.emerus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emerus.R
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.view.Window
import android.widget.Button
import com.example.emerus.MainActivity
import com.example.emerus.Support

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val decorView = window.decorView
        setContentView(R.layout.activity_login)
        val username = findViewById<View>(R.id.username) as TextView
        val password = findViewById<View>(R.id.password) as TextView
        val loginbtn = findViewById<Button>(R.id.loginbtn)
        val supportbtn = findViewById<Button>(R.id.supportbtn)

        //admin and admin
        loginbtn.setOnClickListener { v: View? ->
            if (username.text.toString() == "Admin" && password.text.toString() == "emerus" || username.text.toString() == "Marko" && password.text.toString() == "emerus" || username.text.toString() == "Mario" && password.text.toString() == "emerus") {
                //correct
                Toast.makeText(this@Login, "Dobrodošli " + username.text + ".", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Login, MainActivity::class.java))
            } else  //incorrect
                Toast.makeText(this@Login, "Provjerite Login podatke ili kontaktirajte Administratora", Toast.LENGTH_SHORT).show()
        }
        supportbtn.setOnClickListener { startActivity(Intent(this@Login, Support::class.java)) }
    }
}