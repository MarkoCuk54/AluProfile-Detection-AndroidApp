

package com.example.emerus;
 import androidx.appcompat.app.AppCompatActivity;
 import android.content.Intent;
 import android.net.Uri;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Toast;
    public class Contact extends AppCompatActivity {
        EditText et_subject,et_message;
        Button btn;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contat);
            et_subject = findViewById(R.id.email_subject);
            et_message = findViewById(R.id.email_message);
            btn = findViewById(R.id.email_send);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String subject = et_subject.getText().toString().trim();
                    String message = et_message.getText().toString().trim();
                    String email = "mario.tomic@emerus.eu";
                    if(subject.isEmpty())
                    {
                        Toast.makeText(Contact.this, "Molimo upišite predmet.", Toast.LENGTH_SHORT).show();
                    }
                    else if(message.isEmpty())
                    {
                        Toast.makeText(Contact.this, "Molimo upišite poruku.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String mail = "mailto:" + email +
                                "?&subject=" + Uri.encode(subject) +
                                "&body=" + Uri.encode(message);
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse(mail));
                        try {
                            startActivity(Intent.createChooser(intent,"Send Email.."));
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Contact.this, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

