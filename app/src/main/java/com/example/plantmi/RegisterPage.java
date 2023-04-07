package com.example.plantmi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {
    Button registerBtn, loginBtn;
    FirebaseAuth mAuth;
    TextInputEditText addEmail, addPassword;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private User user;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(RegisterPage.this,PlantProfilePage.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();
        registerBtn=findViewById(R.id.registerbutton);
        loginBtn=findViewById(R.id.loginbutton);
        addEmail=findViewById(R.id.addEmail);
        addPassword=findViewById(R.id.addPassword);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email,password;
                email=addEmail.getText().toString();
                password=addPassword.getText().toString();
                user = new User(email,"Radish","Hi! I am your beloved pet!");

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterPage.this,"Enter email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterPage.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterPage.this, "Account success!",Toast.LENGTH_SHORT).show();

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                    Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterPage.this, "Registration failed. Try a longer password!",Toast.LENGTH_SHORT).show();
                                }
                            }

                            private void updateUI(FirebaseUser currentUser) {
                                if (user != null) {
                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    databaseReference = firebaseDatabase.getReference();//databasereference

                                    String UID = currentUser.getUid();
                                    DatabaseReference userRef = databaseReference.child("users");//Create child node reference
                                    userRef.child(UID).setValue(user);//Insert value to child node
                                }
                            }

                        });

            }
        });

    }
}
