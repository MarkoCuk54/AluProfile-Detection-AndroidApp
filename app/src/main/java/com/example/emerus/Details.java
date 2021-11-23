package com.example.emerus;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {



    // private static final String TAG = "Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Adding Profile to Firebase code :
/*
        // Create a new user with a first and last name
        Map<String, Object> profiles = new HashMap<>();
        profiles.put("profile", "PS1520");
        profiles.put("link", "https://emerus1-my.sharepoint.com/:i:/r/personal/marko_cuk_emerus_eu/Documents/PS15020.png?csf=1&web=1&e=qHksiQ");

// Add a new document with a generated ID
        db.collection("profiles")
                .add(profiles)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot agithdded with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                }); */

        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        View decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("key");
        TextView profile = findViewById(R.id.profile);
        profile.setText(value);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Profili").document(value);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String link = (String) documentSnapshot.get("link");
                System.out.println(link);
                ImageView crtez;
                crtez = findViewById(R.id.image12);
                Glide.with(Details.this).load(link).into(crtez);
            }
        });








    }
}

