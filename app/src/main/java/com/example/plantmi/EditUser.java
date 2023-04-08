package com.example.plantmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditUser extends AppCompatActivity {
    TextInputEditText userName;
    Button saveDetails;
    DatabaseReference rootDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);
        userName = findViewById(R.id.username);
        saveDetails = findViewById(R.id.save);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = user.getUid();
        rootDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = userName.getText().toString();
                if(TextUtils.isEmpty(updatedName)){
                    Intent intent = new Intent(EditUser.this,PlantProfilePage.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    HashMap newUser = new HashMap<>();
                    newUser.put("username",updatedName);
                    newUser.put("user",user.getEmail());
                    rootDatabaseReference.updateChildren(newUser);
                    Intent intent = new Intent(EditUser.this, PlantProfilePage.class);
                    startActivity(intent);
                }
            }
        });




    }
}
