package com.example.emerus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Details extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_details);

        // Getting var from MainActivity
        Bundle extras = getIntent().getExtras();
        String profile_name = extras.getString("profile_name");
        TextView profile = findViewById(R.id.profile);
        profile.setText(profile_name);

        // Getting data from Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Profili").document(profile_name);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            String link = (String) documentSnapshot.get("link");
            String vlasnik =(String) documentSnapshot.get("vlasnik");

            TextView vlasniktxt;
            vlasniktxt = findViewById(R.id.vlasniktxt);
            vlasniktxt.setText(vlasnik);

            ImageView crtez;
            crtez = findViewById(R.id.image12);
            Glide.with(Details.this).load(link).into(crtez);

             Button button = findViewById(R.id.button);
            if(link != ""){
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                 }}
            );}
            if (link == null){
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(Details.this, "Nažalost, nema ništa za preuzimanje. Pokušajte s drugim profilom.", Toast.LENGTH_LONG).show();
                    }});
            };

        });
    }}

