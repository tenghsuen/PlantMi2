package com.example.plantmi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class PlantStatus extends AppCompatActivity {

    View slidingBar;
    DatabaseReference rootDatabaseReference;
    DatabaseReference databaseReference;
    DatabaseReference descRootDatabaseReference;
    DatabaseReference nameRootDatabaseReference;
    private TextView moistureData;
    private TextView lightData;
    ImageButton editBtn;
    TextView nameOfPlant, descOfPlant;
    SensorLight sensorLight;
    SensorSoil sensorSoil;

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
                Intent i = new Intent(PlantStatus.this, EditPlant.class);
                startActivity(i);
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
                if (value<=20){
                    Toast.makeText(PlantStatus.this, "Remember to water mi!", Toast.LENGTH_LONG).show();
                }
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

        databaseReference = rootDatabaseReference.child("sensor_data");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userUID = user.getUid();
        nameRootDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID).child("plantname");
        descRootDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID).child("plantdesc");
        nameRootDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String data = snapshot.getValue().toString();
                    nameOfPlant.setText(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        descRootDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String data = snapshot.getValue().toString();
                    descOfPlant.setText(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
