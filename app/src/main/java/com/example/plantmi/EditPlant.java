package com.example.plantmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditPlant extends AppCompatActivity {
    EditText plantName, plantDescription;
    Button saveDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plantName = findViewById(R.id.plantname);
        plantDescription = findViewById(R.id.plantdescription);
        saveDetails = findViewById(R.id.save);

        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPlant.this, PlantStatus.class);
                startActivity(intent);
            }
        });
    }
}
