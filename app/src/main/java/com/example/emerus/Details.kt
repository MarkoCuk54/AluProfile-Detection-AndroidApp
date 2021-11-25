package com.example.emerus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.example.emerus.R
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.bumptech.glide.Glide

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val decorView = window.decorView
        setContentView(R.layout.activity_details)
        val extras = intent.extras
        val value = extras!!.getString("profile_name")
        val profile = findViewById<TextView>(R.id.profile)
        profile.text = value

        // Getting data from Firebase
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Profili").document(value!!)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val link = documentSnapshot["link"] as String?
            val vlasnik = documentSnapshot["vlasnik"] as String?

            val vlasniktxt: TextView
            vlasniktxt = findViewById(R.id.vlasniktxt)
            vlasniktxt.text = vlasnik

            val crtez: ImageView
            crtez = findViewById(R.id.image12)
            Glide.with(this@Details).load(link).into(crtez)
        }
    }
}