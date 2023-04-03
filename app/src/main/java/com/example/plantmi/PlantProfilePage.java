package com.example.plantmi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlantProfilePage extends AppCompatActivity {
    Button openCamera, waterBtn, logoutBtn, galleryBtn;
    ImageView imageView;
    TextView addImgText;
    FirebaseAuth auth;
    View plantStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantprofile);
        openCamera = findViewById(R.id.OpenCamera);
        waterBtn = findViewById(R.id.waterButton);
        galleryBtn = findViewById(R.id.GalleryButton);
        logoutBtn = findViewById(R.id.logout);
        imageView = findViewById(R.id.capturedImage);
        plantStatus = findViewById(R.id.plantstatus);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        
        // Check if user is logged in
        if (currentUser != null) { 
            // User signed in successfully, check if they have a picture stored in Firebase Storage
            String uid = currentUser.getUid();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference photoRef = storageRef.child("images/" + currentUser.getEmail() + "/" + uid);

            photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Check if the image belongs to the current user
                    String filename = uri.getLastPathSegment();
                    if (filename != null && filename.contains(uid)) {
                        // Picture found, load it into the ImageView
                        Picasso.get().load(uri).into(imageView);
                    } else {
                        // Image does not belong to current user, do nothing
                        Toast.makeText(PlantProfilePage.this, "Image Unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Profile picture not found, do nothing
                    Toast.makeText(PlantProfilePage.this, "Image retrieve unsuccessfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Uri photoUri = result.getData().getData();
                            StorageReference storageRef=FirebaseStorage.getInstance().getReference();
                            //Create reference of img file in firebase storage
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            StorageReference photoRef = storageRef.child("images/"+currentUser.getEmail()+"/"+ uid);

                            //Upload image to firebase storage
                            //convert image data into byte array
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(photoUri);
                                byte[] yourPhotoToByteArray = getBytes(inputStream);

                                UploadTask uploadTask = photoRef.putBytes(yourPhotoToByteArray);
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Image uploaded successfully, load it into the ImageView
                                        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Picasso.get().load(uri).into(imageView);
                                                Toast.makeText(PlantProfilePage.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(PlantProfilePage.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    public byte[] getBytes(InputStream inputStream) throws IOException {
                        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                        int bufferSize = 1024;
                        byte[] buffer = new byte[bufferSize];

                        int len = 0;
                        while ((len = inputStream.read(buffer)) != -1) {
                            byteBuffer.write(buffer, 0, len);
                        }

                        return byteBuffer.toByteArray();
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


