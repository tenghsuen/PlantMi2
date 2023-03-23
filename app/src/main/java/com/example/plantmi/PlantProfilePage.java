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

public class PlantProfilePage extends AppCompatActivity {
    Button openCamera, waterBtn;
    ImageView imageView;
    private final int camera_code=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantprofile);
        openCamera = findViewById(R.id.OpenCamera);
        waterBtn = findViewById(R.id.waterButton);
        imageView=findViewById(R.id.imageView);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,camera_code);
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
            super.onActivityResult(resultCode,resultCode,data);
            if (resultCode==RESULT_OK){
              if (resultCode==camera_code){
                  Bitmap image= (Bitmap)data.getExtras().get("data");
                  imageView.setImageBitmap(image);
              }
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
