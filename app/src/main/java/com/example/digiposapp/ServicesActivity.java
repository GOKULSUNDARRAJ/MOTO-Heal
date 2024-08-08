package com.example.digiposapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String vehicleName;
    String vehicleNumber,vehicleImage,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Window window=getWindow();
        window.setBackgroundDrawableResource(R.drawable.button_bg);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("vehicle_name") && intent.hasExtra("vehicle_number") && intent.hasExtra("vehicle_image")) {
            vehicleName = intent.getStringExtra("vehicle_name");
            vehicleNumber = intent.getStringExtra("vehicle_number");
            vehicleImage = intent.getStringExtra("vehicle_image");
            address=intent.getStringExtra("address");


        }

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Retrieve service data from Firebase
        retrieveServiceData();
    }




    private void retrieveServiceData() {
        mDatabase.child("service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Service> services = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String serviceName = snapshot.child("service_name").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    services.add(new Service(serviceName,image));
                }
                // Bind the data to the RecyclerView
                setupRecyclerView(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(ServicesActivity.this, "Failed to retrieve data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(List<Service> services) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ServiceAdapter adapter = new ServiceAdapter(services, vehicleName, vehicleNumber, vehicleImage,address);
        recyclerView.setAdapter(adapter);
    }



}
