package com.example.digiposapp;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MapFragment.AddressListener {
    private RecyclerView recyclerView;
    private VehicleAdapter adapter;
    private List<Vehicle> vehicleList;
    private FirebaseAuth mAuth;



    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window=getWindow();
        window.setBackgroundDrawableResource(R.drawable.button_bg);



        MapFragment fragment=new MapFragment();
        fragment.setAddressListener((MapFragment.AddressListener) MainActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {

                } else if (itemId == R.id.nav_profile) {
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                } else if (itemId == R.id.nav_settings) {
                    // Handle Settings click
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                } else if (itemId == R.id.nav_logout) {
                    CustomDialogClassfoeadd45 cdd = new CustomDialogClassfoeadd45(MainActivity.this);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                }

                // Close the drawer when an item is selected
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserUid = firebaseAuth.getCurrentUser().getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(currentUserUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the user data exists
                if (dataSnapshot.exists()) {
                    // User data exists, retrieve the username
                    String username = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    // Set the username in the TextView
                    TextView navHeaderUsername = findViewById(R.id.nav_header_username);
                    TextView textViewEmail = findViewById(R.id.nav_header_username3);
                    TextView imageView2=findViewById(R.id.imageView2);


                    navHeaderUsername.setText(username);
                    textViewEmail.setText(email);
                    imageView2.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(MainActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for the menu item to open the drawer
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        // LinearLayoutManager with horizontal orientation
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        vehicleList = new ArrayList<>();
        adapter = new VehicleAdapter(vehicleList);
        recyclerView.setAdapter(adapter);


    }

    private void retrieveVehicleDetails(String address) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userVehiclesRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(currentUser.getUid())
                    .child("Vehicles");

            userVehiclesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    vehicleList.clear();
                    for (DataSnapshot vehicleSnapshot : dataSnapshot.getChildren()) {
                        String name = vehicleSnapshot.child("name").getValue(String.class);
                        String number = vehicleSnapshot.child("number").getValue(String.class);
                        String image = vehicleSnapshot.child("image").getValue(String.class);
                        String address1 = address; // Replace this with the actual address retrieval logic

                        // Create a new Vehicle object with address
                        Vehicle vehicle = new Vehicle(name, number, image, address1);
                        vehicleList.add(vehicle);

                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Failed to retrieve vehicle details", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void gotoaddv(View view) {
        CustomDialogClassfoeaddaddv cdd = new CustomDialogClassfoeaddaddv(MainActivity.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }




    private void showCustomDialog() {
        CustomDialogClassfoeadd cdd = new CustomDialogClassfoeadd(this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }


    public void gotoEmergency(View view) {
        startActivity(new Intent(view.getContext(),EmergencyActivity.class));
    }
    @Override
    public void onAddressReceived(String address) {
        retrieveVehicleDetails(address);
    }



    private void showSizeIncreaseNotification() {
        // Create an explicit intent for launching the MainActivity when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the current time
        String currentTime = getCurrentTime();

        // Ensure you have a valid small icon resource (R.drawable.ic_notification) to set
        int smallIcon = R.drawable.baseline_electric_car_24; // Replace with your small icon resource

        // Ensure you have a valid large icon resource
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.lr); // Replace with your large icon resource

        // Ensure you have a valid large image resource
        Bitmap largeImage = BitmapFactory.decodeResource(getResources(), R.drawable.lr); // Replace with your large image resource

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "list_size_channel")
                .setSmallIcon(smallIcon) // Set the valid small icon
                .setContentTitle("Moto Heal Service ")
                .setContentText("Please get new service with Moto Heal with new Offers" + " at " + currentTime)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setLargeIcon(largeIcon) // Set the large icon
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(largeImage)
                        .bigLargeIcon(null)); // Optionally set large icon for big picture style

        // Show the notification using the NotificationManager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Create the notification channel (required for Android 8.0 and above)
        createNotificationChannel();

        // Notify with a unique notification ID
        notificationManager.notify(1, builder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ListSizeChannel";
            String description = "Channel for list size notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("list_size_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

}


