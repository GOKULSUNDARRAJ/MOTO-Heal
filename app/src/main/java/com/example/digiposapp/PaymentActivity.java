package com.example.digiposapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    String vehicleName, vehicleNumber, vehicleImage, serviceName, address1;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,wholeserviceadmin;
    String toastMessage;
    String message;

    private static final int PHONEPE_REQUEST_CODE = 123; // Use any unique integer value
    int PAYTM_REQUEST_CODE=123;

    String vehicleName2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Window window=getWindow();
        window.setBackgroundDrawableResource(R.drawable.button_bg);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("UserService");

        wholeserviceadmin=FirebaseDatabase.getInstance().getReference().child("WholeService");

        // Assuming radioButton1 is a member variable
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);

        Intent intent = getIntent();

        vehicleName = intent.getStringExtra("vehicle_name");
        vehicleNumber = intent.getStringExtra("vehicle_number");
        vehicleImage = intent.getStringExtra("vehicle_image");
        address1 = intent.getStringExtra("address");
        serviceName = intent.getStringExtra("service_name");

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
                    vehicleName2 = dataSnapshot.child("vehicleName").getValue(String.class);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(PaymentActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            }
        });


        Toast.makeText(this, vehicleName +vehicleNumber+serviceName+address1+vehicleImage , Toast.LENGTH_SHORT).show();


        CardView scancard4 = findViewById(R.id.scancard4);

        scancard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (radioButton1.isChecked()) {
                    // Google Pay selected
                    toastMessage = "Google Pay";
                    Uri uri =
                            new Uri.Builder()
                                    .scheme("upi")
                                    .authority("pay")
                                    .appendQueryParameter("pa", "gokulsundar4545@okaxis")
                                    .appendQueryParameter("pn", "your-merchant-name")
                                    .appendQueryParameter("mc", "your-merchant-code")
                                    .appendQueryParameter("tr", "your-transaction-ref-id")
                                    .appendQueryParameter("tn", "your-transaction-note")
                                    .appendQueryParameter("am", "100")
                                    .appendQueryParameter("cu", "INR")
                                    .appendQueryParameter("url", "your-transaction-url")
                                    .build();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
                    startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

                } else if (radioButton2.isChecked()) {
                    // Paytm selected
                    toastMessage = "Paytm";

                    Uri uri = new Uri.Builder()
                            .scheme("upi")
                            .authority("pay")
                            .appendQueryParameter("pa", "your-paytm-merchant-identifier@paytm")
                            .appendQueryParameter("pn", "Paytm Merchant Name")
                            .appendQueryParameter("mc", "your-merchant-code")
                            .appendQueryParameter("tid", "your-transaction-id")
                            .appendQueryParameter("tn", "your-transaction-note")
                            .appendQueryParameter("am", "100")
                            .appendQueryParameter("cu", "INR")
                            .build();

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setPackage("net.one97.paytm"); // Package name for Paytm app

// Start activity for result
                    startActivityForResult(intent, PAYTM_REQUEST_CODE);


                    // Your Paytm payment handling code here
                } else if (radioButton3.isChecked()) {
                    // PhonePe selected
                    toastMessage = "PhonePe";

                    Uri uri = new Uri.Builder()
                            .scheme("upi")
                            .authority("pay")
                            .appendQueryParameter("pa", "gokulsundar4545@okaxis")
                            .appendQueryParameter("pn", "your-merchant-name")
                            .appendQueryParameter("mc", "your-merchant-code")
                            .appendQueryParameter("tr", "your-transaction-ref-id")
                            .appendQueryParameter("tn", "your-transaction-note")
                            .appendQueryParameter("am", "1")
                            .appendQueryParameter("cu", "INR")
                            .appendQueryParameter("url", "your-transaction-url")
                            .build();

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
// Set the PhonePe package name
                    intent.setPackage("com.phonepe.app"); // Package name for PhonePe app

// Start activity for result
                    startActivityForResult(intent, PHONEPE_REQUEST_CODE);
                    // Your PhonePe payment handling code here
                } else {
                    // Cash on Delivery selected
                    toastMessage = "Cash on Delivery";

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);


                    String currentDateTime = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);


                    Payment payment = new Payment(vehicleName, vehicleNumber, vehicleImage, address1, serviceName,toastMessage,currentDateTime,message,vehicleName2);
                    mDatabase.push().setValue(payment);
                    wholeserviceadmin.push().setValue(payment);



                    CustomDialogClassfoeadd cdd = new CustomDialogClassfoeadd(PaymentActivity.this);

                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                    // Your Cash on Delivery handling code here
                    showSizeIncreaseNotification();
                }

                // Show the toast based on the selected payment method
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();




            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_PAY_REQUEST_CODE) {

            switch (resultCode) {
                case RESULT_OK:
                    message = "Payment successful";

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);


                    String currentDateTime = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);


                    Payment payment = new Payment(vehicleName, vehicleNumber, vehicleImage, address1, serviceName,toastMessage,currentDateTime,message,vehicleName2);
                    mDatabase.push().setValue(payment);
                    wholeserviceadmin.push().setValue(payment);

                    CustomDialogClassfoeadd cdd = new CustomDialogClassfoeadd(PaymentActivity.this);

                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();

                    showSizeIncreaseNotification();
                    break;
                case RESULT_CANCELED:
                    message = "Payment canceled";
                    CustomDialogClassfoeaddcash cdd3 = new CustomDialogClassfoeaddcash(PaymentActivity.this);

                    cdd3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd3.show();
                    showSizeIncreaseNotificationfail();
                    break;
                default:
                    message = "Payment failed";

                    CustomDialogClassfoeaddcash cdd5 = new CustomDialogClassfoeaddcash(PaymentActivity.this);

                    cdd5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd5.show();
                    showSizeIncreaseNotificationfail();
                    break;
            }
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }


        if (resultCode==PHONEPE_REQUEST_CODE){
            switch (resultCode) {
                case RESULT_OK:
                    message = "Payment successful";



                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);


                    String currentDateTime = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);


                    Payment payment = new Payment(vehicleName, vehicleNumber, vehicleImage, address1, serviceName,toastMessage,currentDateTime,message,vehicleName2);
                    mDatabase.push().setValue(payment);

                    wholeserviceadmin.push().setValue(payment);

                    CustomDialogClassfoeadd cdd = new CustomDialogClassfoeadd(PaymentActivity.this);

                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                    showSizeIncreaseNotification();
                    break;
                case RESULT_CANCELED:
                    message = "Payment canceled";

                    CustomDialogClassfoeaddcash cdd3 = new CustomDialogClassfoeaddcash(PaymentActivity.this);
                    cdd3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd3.show();
                    showSizeIncreaseNotificationfail();
                    break;
                default:
                    message = "Payment failed";

                    CustomDialogClassfoeaddcash cdd5 = new CustomDialogClassfoeaddcash(PaymentActivity.this);
                    cdd5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd5.show();
                    showSizeIncreaseNotificationfail();

                    break;
            }
        }

        if (resultCode==PAYTM_REQUEST_CODE){


            switch (resultCode) {
                case RESULT_OK:
                    message = "Payment successful";

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);


                    String currentDateTime = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);


                    Payment payment = new Payment(vehicleName, vehicleNumber, vehicleImage, address1, serviceName,toastMessage,currentDateTime,message,vehicleName2);
                    mDatabase.push().setValue(payment);

                    wholeserviceadmin.push().setValue(payment);

                    CustomDialogClassfoeadd cdd = new CustomDialogClassfoeadd(PaymentActivity.this);

                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();

                    showSizeIncreaseNotification();
                    break;
                case RESULT_CANCELED:
                    message = "Payment canceled";

                    CustomDialogClassfoeaddcash cdd3 = new CustomDialogClassfoeaddcash(PaymentActivity.this);

                    cdd3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd3.show();
                    showSizeIncreaseNotificationfail();
                    break;
                default:
                    message = "Payment failed";
                    CustomDialogClassfoeaddcash cdd5 = new CustomDialogClassfoeaddcash(PaymentActivity.this);

                    cdd5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd5.show();
                    showSizeIncreaseNotificationfail();
                    break;
            }


        }
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
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ss); // Replace with your large icon resource

        // Ensure you have a valid large image resource
        Bitmap largeImage = BitmapFactory.decodeResource(getResources(), R.drawable.ss); // Replace with your large image resource

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "list_size_channel")
                .setSmallIcon(smallIcon) // Set the valid small icon
                .setContentTitle("Moto Heal Service")
                .setContentText("Order Service  is  Successful,Your Server Partner Arrive within arround 15 minutes"+ currentTime)
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


    private void showSizeIncreaseNotificationfail() {
        // Create an explicit intent for launching the MainActivity when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the current time
        String currentTime = getCurrentTime();

        // Ensure you have a valid small icon resource (R.drawable.ic_notification) to set
        int smallIcon = R.drawable.baseline_electric_car_24; // Replace with your small icon resource

        // Ensure you have a valid large icon resource
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ff); // Replace with your large icon resource

        // Ensure you have a valid large image resource
        Bitmap largeImage = BitmapFactory.decodeResource(getResources(), R.drawable.ff); // Replace with your large image resource

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "list_size_channel")
                .setSmallIcon(smallIcon) // Set the valid small icon
                .setContentTitle("Moto Heal Service")
                .setContentText("Order Service  is  Unsuccess,Try Again"+ currentTime)
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


}

