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

public class StartPage extends AppCompatActivity {
    Button photoBtn;
    Button waterBtn;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        photoBtn=findViewById(R.id.photoButton);
        waterBtn=findViewById(R.id.waterButton);
        photoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo=(Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(photo);
    }
}
