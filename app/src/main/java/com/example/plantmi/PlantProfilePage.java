package com.example.plantmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PlantProfilePage extends AppCompatActivity {
    Button openCamera, waterBtn, logoutBtn;
    ImageView imageView;
    TextView addImgText;
    private final int camera_code=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantprofile);
        openCamera = findViewById(R.id.OpenCamera);
        waterBtn = findViewById(R.id.waterButton);
        logoutBtn=findViewById(R.id.logout);
        addImgText = findViewById(R.id.simpleTextView);
        imageView=findViewById(R.id.capturedImage);
        waterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantProfilePage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,camera_code);
            }
        });
        //waterBtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //Intent intent = new Intent(PlantProfilePage.this, PlantStatus.class);
                //startActivity(intent);
        //    }
        //});
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PlantProfilePage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            if (requestCode==camera_code){
                Bitmap image= (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(image);
                addImgText.setVisibility(View.INVISIBLE);

            }
    }


    //@Override
    //protected void onActivityResult(int requestCode)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap photo=(Bitmap)data.getExtras().get("data");
//        imageView.setImageBitmap(photo);
//    }
}
