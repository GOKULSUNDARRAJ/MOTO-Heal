package com.example.digiposapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar; // Add this line

    Button signupButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar); // Initialize progressBar

        // Your signup button click listener
        signupButton = findViewById(R.id.login);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        progressBar.setVisibility(View.VISIBLE); // Show progressBar when signup starts
        signupButton.setVisibility(View.INVISIBLE);

        EditText emailEditText = findViewById(R.id.emailedt);
        EditText passwordEditText = findViewById(R.id.passdt);
        EditText nameEditText = findViewById(R.id.nameedi);
        EditText vehicleNameEditText = findViewById(R.id.vname);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String vehicleName = vehicleNameEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE); // Hide progressBar when signup completes
                        signupButton.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Save additional user data to the database
                                saveUserData(user.getUid(), email, name, vehicleName);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserData(String userId, String email, String name, String vehicleName) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User(userId, email, name, vehicleName);
        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // User data saved successfully
                        Toast.makeText(SignupActivity.this, "User signed up successfully!",
                                Toast.LENGTH_SHORT).show();
                        // Navigate to the next activity or perform other actions
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        Toast.makeText(SignupActivity.this, "Failed to save user data: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void gotologin(View view) {
        startActivity(new Intent(view.getContext(),LoginActivity.class));
    }
}
