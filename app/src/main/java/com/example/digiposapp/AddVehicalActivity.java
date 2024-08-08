package com.example.digiposapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class AddVehicalActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText vehicleNameEditText, vehicleNumberEditText;
    private ImageView imageView,imageviewshow;
    CardView updateButton;
    private Bitmap selectedImageBitmap;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehical);


        Window window=getWindow();
        window.setBackgroundDrawableResource(R.drawable.button_bg);
        // Initialize Firebase components
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        updateButton=findViewById(R.id.scancard4);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateButton.setVisibility(View.GONE);
                uploadVehicleData(view);
            }
        });

        // Initialize Views
        vehicleNameEditText = findViewById(R.id.vnumbername);
        vehicleNumberEditText = findViewById(R.id.vnumber);
        imageView = findViewById(R.id.imageView33);
        imageviewshow=findViewById(R.id.image4);

        // Set OnClickListener for ImageView
        imageView.setOnClickListener(view -> openGallery());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imageviewshow.setImageBitmap(selectedImageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Handle the button click event to upload vehicle data
    public void uploadVehicleData(View view) {
        String vehicleName = vehicleNameEditText.getText().toString().trim();
        String vehicleNumber = vehicleNumberEditText.getText().toString().trim();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            if (!vehicleName.isEmpty() && !vehicleNumber.isEmpty()) {
                if (selectedImageBitmap != null) {
                    uploadImageToFirebase(selectedImageBitmap, vehicleName, vehicleNumber);
                } else {
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebase(Bitmap imageBitmap, String vehicleName, String vehicleNumber) {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("vehicle_images/" + System.currentTimeMillis() + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                saveVehicleToDatabase(vehicleName, vehicleNumber, imageUrl);

                updateButton.setVisibility(View.VISIBLE);
            });
        }).addOnFailureListener(e -> {
            updateButton.setVisibility(View.VISIBLE);
            Toast.makeText(AddVehicalActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveVehicleToDatabase(String vehicleName, String vehicleNumber, String imageUrl) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userVehiclesRef = databaseReference.child(currentUser.getUid()).child("Vehicles").push();
            userVehiclesRef.child("name").setValue(vehicleName);
            userVehiclesRef.child("number").setValue(vehicleNumber);
            userVehiclesRef.child("image").setValue(imageUrl);
            Toast.makeText(this, "Vehicle data uploaded successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after successful upload
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
