package com.example.plantmi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlantStatus extends AppCompatActivity {

    View slidingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantstatus);

        slidingBar=findViewById(R.id.plantStatusSlidingBar);

        slidingBar.setOnTouchListener(new OnSwipeTouchListener(PlantStatus.this) {
            public void onSwipeBottom() {
                Intent i = new Intent(PlantStatus.this, PlantProfilePage.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_from_top, R.anim.slide_in_top );
            }
        });
    }


}
