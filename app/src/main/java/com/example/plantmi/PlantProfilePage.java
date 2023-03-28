package com.example.plantmi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PlantProfilePage extends AppCompatActivity {
    Button openCamera, waterBtn, logoutBtn, galleryBtn;
    ImageView imageView;
    TextView addImgText;
    private final int camera_code=100;
    View plantStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantprofile);
        openCamera = findViewById(R.id.OpenCamera);
        waterBtn = findViewById(R.id.waterButton);
        galleryBtn=findViewById(R.id.GalleryButton);
        logoutBtn=findViewById(R.id.logout);
        addImgText = findViewById(R.id.simpleTextView);
        imageView=findViewById(R.id.capturedImage);
        plantStatus=findViewById(R.id.plantstatus);
        waterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantProfilePage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final ActivityResultLauncher<Intent> launcherGallery = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if( result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null){
                            Uri photoUri = result.getData().getData();
                            imageView.setImageURI(photoUri);

                        }
                    }
                }
        );
        ActivityResultLauncher<Intent> launcherCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bundle b = result.getData().getExtras();
                        Bitmap thumbnail = (Bitmap) b.get("data");
                        imageView.setImageBitmap(thumbnail);
                    }
                }
        );
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcherGallery.launch(intent);
            }
        });
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcherCamera.launch(intent);

            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PlantProfilePage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        plantStatus.setOnTouchListener(new OnSwipeTouchListener(PlantProfilePage.this) {
            public void onSwipeTop() {
                Intent i = new Intent(PlantProfilePage.this, PlantStatus.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });
    }
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
//            super.onActivityResult(requestCode,resultCode,data);
//            if (requestCode==camera_code){
//                Bitmap image= (Bitmap)data.getExtras().get("data");
//                imageView.setImageBitmap(image);
//                addImgText.setVisibility(View.INVISIBLE);
//
//            }
       }


