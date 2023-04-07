package com.example.plantmi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class PlantStatus extends AppCompatActivity {

    View slidingBar;
    DatabaseReference rootDatabaseReference;
    private TextView moistureData;
    private TextView lightData;
    SensorSoil sensorSoil;
    SensorLight sensorLight;
    ImageButton editBtn;
    TextView nameOfPlant, descOfPlant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantstatus);

        slidingBar = findViewById(R.id.plantStatusSlidingBar);
        editBtn = findViewById(R.id.edit);
        nameOfPlant = findViewById(R.id.name);
        descOfPlant = findViewById(R.id.desc);

        slidingBar.setOnTouchListener(new OnSwipeTouchListener(PlantStatus.this) {
            public void onSwipeBottom() {
                Intent i = new Intent(PlantStatus.this, PlantProfilePage.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_from_top, R.anim.slide_in_top );
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantStatus.this, EditPlant.class);
                startActivity(intent);
            }
        });

        rootDatabaseReference = FirebaseDatabase.getInstance().getReference();
        //databaseReference = rootDatabaseReference.child("sensor_data");
        moistureData = findViewById(R.id.moistureLevelValue);
        lightData = findViewById(R.id.lightIntensityValue);

        rootDatabaseReference.child("sensor_soil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensorSoil = snapshot.getValue(SensorSoil.class);
                Log.d("Firebase",sensorSoil.getValue().toString());
                double d = Double.parseDouble(sensorSoil.getValue().toString());
                double value = Math.round( (100 - ((d/4095)*100)) );
                moistureData.setText(Double.toString(value) + "%");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(PlantStatus.this, "Failed to get Moisture Level data.", Toast.LENGTH_SHORT).show();
            }
        });

        rootDatabaseReference.child("sensor_light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensorLight = snapshot.getValue(SensorLight.class);
                Log.d("Firebase",sensorLight.getValue().toString());
                lightData.setText(sensorLight.getValue().toString() + "units");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(PlantStatus.this, "Failed to get Light Intensity data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

//     private void getData() {

//         databaseReference.addValueEventListener(new ValueEventListener() {
//             @Override
//             public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 for ( DataSnapshot postSnapshot: snapshot.getChildren() ){
//                     if (postSnapshot.equals("value")) {
//                         String value = postSnapshot.getValue(String.class);
//                         moistureData.setText(value);
//                     }
//                 }
//             }

//             @Override
//             public void onCancelled(@NonNull DatabaseError error) {
//                 // calling on cancelled method when we receive
//                 // any error or we are not able to get the data.
//                 Toast.makeText(PlantStatus.this, "Failed to get data.", Toast.LENGTH_SHORT).show();
//             }
//         });
//     }

}
