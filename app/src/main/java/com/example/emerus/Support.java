package com.example.emerus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Support extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        View decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_support);

        Button contactbtn = findViewById(R.id.contactbtn);
        contactbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Support.this, Contact.class)) ;

             }
         });

}}

