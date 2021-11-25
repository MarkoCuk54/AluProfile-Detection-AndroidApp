package com.example.emerus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.emerus.R
import android.content.Intent
import android.view.View
import android.view.Window
import com.example.emerus.Login
import android.widget.TextView
import java.util.*

class SplashActivitiy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility = uiOptions
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_activitiy)
        val splashScreenStarter: Thread = object : Thread() {
            override fun run() {
                try {
                    var delay = 0
                    while (delay < 4000) {
                        sleep(150)
                        delay = delay + 100
                    }
                    startActivity(Intent(this@SplashActivitiy, Login::class.java))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    finish()
                }
            }
        }
        splashScreenStarter.start()

        // Slucajni Tip u vezi Aluminja u Splash Screenu :
        val tips = findViewById<TextView>(R.id.tips)
        val arr = arrayOf("Kutovi bi u pravilu trebali biti zaobljeni ili polumjerni. Zaobljeni kutovi su mnogo jači i lakši za istiskivanje. Normalni polumjeri su 0,4 do 1,0 mm.",
                "Manje šupljina (ili udubljenja) u vašem dijelu može olakšati proizvodnju i smanjiti težinu metala, čime se smanjuju troškovi. Također pojednostavljuje dizajn matrice i može dovesti do niže cijene matrice.",
                "Ako će vaš odjel zahtijevati bušenje, probijanje ili montažu nakon ekstruzije, možda ćete htjeti olakšati posao strojarima uključivanjem indeksnih linija kako biste osigurali točnost.",
                "Kada dimenzionirate svoj dizajn, pokušajte odrediti dimenzije i bilo koje posebne tolerancije od metalne točke do metalne točke, a ne do središnje linije ili rupe.")
        val r = Random()
        val randomNumber = r.nextInt(arr.size)
        tips.text = arr[randomNumber]
    }
}