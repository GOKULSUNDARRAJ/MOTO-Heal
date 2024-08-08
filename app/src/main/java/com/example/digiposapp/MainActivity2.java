package com.example.digiposapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WholeServiceAdapter adapter;
    private DatabaseReference databaseReference;
    private int previousListSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WholeServiceAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Initialize Firebase Realtime Database reference
        DatabaseReference userRef =  FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("UserService");

        // Attach a listener to retrieve data
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<WholeService> wholeServiceList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String serviceName = snapshot.child("serviceName").getValue(String.class);
                    String vehicleName = snapshot.child("vehicleName").getValue(String.class);
                    String vehicleNumber = snapshot.child("vehicleNumber").getValue(String.class);
                    String vehicleImage = snapshot.child("vehicleImage").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String dateTime = snapshot.child("dateTime").getValue(String.class);
                    String paymentSuccess = snapshot.child("paymentSuccess").getValue(String.class);
                    String paymentType = snapshot.child("paymenttype").getValue(String.class);
                    String phone = snapshot.child("phome").getValue(String.class);

                    WholeService service = new WholeService(serviceName, vehicleName, vehicleNumber,
                            vehicleImage, address, dateTime, paymentSuccess, paymentType,phone);


                    wholeServiceList.add(service);
                }

                Collections.reverse(wholeServiceList);
                // Check if the list size has increased
                if (wholeServiceList.size() > previousListSize) {
                    int newSize = wholeServiceList.size();
                    showSizeIncreaseNotification(newSize);
                }

                // Update RecyclerView with retrieved data
                adapter.setWholeServiceList(wholeServiceList);

                // Update the previousListSize
                previousListSize = wholeServiceList.size();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error fetching data", databaseError.toException());
                String errorMessage = "Error fetching data: " + databaseError.getMessage();
                Toast.makeText(MainActivity2.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSizeIncreaseNotification(int newSize) {
        // Create an explicit intent for launching the MainActivity when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the current time
        String currentTime = getCurrentTime();

        // Ensure you have a valid small icon resource (R.drawable.ic_notification) to set
        int smallIcon = R.drawable.baseline_electric_car_24; // Replace with your small icon resource

        // Ensure you have a valid large icon resource
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.large); // Replace with your large icon resource

        // Ensure you have a valid large image resource
        Bitmap largeImage = BitmapFactory.decodeResource(getResources(), R.drawable.large); // Replace with your large image resource

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "list_size_channel")
                .setSmallIcon(smallIcon) // Set the valid small icon
                .setContentTitle("Moto Heal Service Last Service")
                .setContentText("Moto Heal Service Last Service,Please get new service with Moto Heal with new Offers" + newSize + " at " + currentTime)
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
