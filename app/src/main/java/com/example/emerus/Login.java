package com.example.emerus;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        View decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_login);


        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        Button loginbtn = findViewById(R.id.loginbtn);
        Button supportbtn = findViewById(R.id.supportbtn);

        //admin and admin

        loginbtn.setOnClickListener(v -> {
            if(username.getText().toString().equals("Admin") && password.getText().toString().equals("emerus")  || username.getText().toString().equals("Marko") && password.getText().toString().equals("emerus") || username.getText().toString().equals("Mario") && password.getText().toString().equals("emerus") ){
                //correct
                Toast.makeText(Login.this,"Dobrodošli " + username.getText() + "!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, MainActivity.class));

            }else
                //incorrect
                Toast.makeText(Login.this,"Provjerite Login podatke ili kontaktirajte Administratora",Toast.LENGTH_SHORT).show();
        });
        supportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Support.class)) ;
            }
        });

    }
}