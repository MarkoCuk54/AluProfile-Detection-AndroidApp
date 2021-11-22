package com.example.emerus;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    private static final String TAG = "Details";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
    }
}

