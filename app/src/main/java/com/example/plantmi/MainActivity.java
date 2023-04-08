package com.example.plantmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button timeBtn, logoutBtn, waterButton;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutBtn=findViewById(R.id.logout);
        auth=FirebaseAuth.getInstance();
        timeBtn=findViewById(R.id.setTimeButton);
        waterButton=findViewById(R.id.waterButton);
        textView=findViewById(R.id.user_email);
        user=auth.getCurrentUser();
        if (user==null){
            Intent intent = new Intent(MainActivity.this, PlantProfilePage.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }
        //TODO 1.6 for timeButton, invoke the setOnClickListener method
        timeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, PlantProfilePage.class);
                startActivity(intent);
            }
        });
        
        waterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                waterRef.setValue(true);
            }
        });
        
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
