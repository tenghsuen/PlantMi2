package com.example.plantmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditPlant extends AppCompatActivity {
    TextInputEditText plantName, plantDescription;
    Button saveDetails;
    DatabaseReference rootDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editplant);
        plantName = findViewById(R.id.plantname);
        plantDescription = findViewById(R.id.plantdescription);
        saveDetails = findViewById(R.id.save);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = user.getUid();
        rootDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = plantName.getText().toString();
                String updatedDesc = plantDescription.getText().toString();
                if(TextUtils.isEmpty(updatedName)){
                    if(TextUtils.isEmpty(updatedDesc)){
                        Intent intent = new Intent(EditPlant.this,PlantStatus.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    HashMap newUser = new HashMap<>();
                    newUser.put("plantdesc",updatedDesc);
                    newUser.put("plantname",updatedName);
                    newUser.put("user",user.getEmail());
                    rootDatabaseReference.updateChildren(newUser);

                    Intent intent = new Intent(EditPlant.this, PlantStatus.class);
                    startActivity(intent);
                }

            }
        });




    }
}
