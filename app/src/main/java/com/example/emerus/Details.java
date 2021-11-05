package com.example.emerus;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        View decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("key");
        TextView profile = findViewById(R.id.profile);
        profile.setText(value);




    }}

