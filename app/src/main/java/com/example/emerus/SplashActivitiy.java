package com.example.emerus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;



import java.util.Random;

public class SplashActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splash_activitiy);
        Thread splashScreenStarter = new Thread() {
            public void run() {
                try {
                    int delay = 0;
                    while (delay < 4000) {
                        sleep(150);
                        delay = delay + 100;
                    }
                    startActivity(new Intent(SplashActivitiy.this, Login.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }

        };
        splashScreenStarter.start();

        // Slucajni Tip u vezi Aluminja u Splash Screenu :
        TextView tips =  findViewById(R.id.tips);
        String[] arr={"Kutovi bi u pravilu trebali biti zaobljeni ili polumjerni. Zaobljeni kutovi su mnogo jači i lakši za istiskivanje. Normalni polumjeri su 0,4 do 1,0 mm.",
                        "Manje šupljina (ili udubljenja) u vašem dijelu može olakšati proizvodnju i smanjiti težinu metala, čime se smanjuju troškovi. Također pojednostavljuje dizajn matrice i može dovesti do niže cijene matrice.",
                "Ako će vaš odjel zahtijevati bušenje, probijanje ili montažu nakon ekstruzije, možda ćete htjeti olakšati posao strojarima uključivanjem indeksnih linija kako biste osigurali točnost.",
                "Kada dimenzionirate svoj dizajn, pokušajte odrediti dimenzije i bilo koje posebne tolerancije od metalne točke do metalne točke, a ne do središnje linije ili rupe."};
        Random r=new Random();
        int randomNumber=r.nextInt(arr.length);
        tips.setText(arr[randomNumber]);
    }
}