package com.example.digiposapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView paymenttypeTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView image=findViewById(R.id.image);


        Intent intent = getIntent();
        String vehicleImage = intent.getStringExtra("vehicleImage");


        Picasso.get().load(vehicleImage).into(image);

        String serviceName = intent.getStringExtra("serviceName");
        String vehicleName = intent.getStringExtra("vehicleName");
        String vehicleNumber = intent.getStringExtra("vehicleNumber");
        String address = intent.getStringExtra("address");
        String dateTime = intent.getStringExtra("dateTime");
        String paymentSuccess = intent.getStringExtra("paymentSuccess");
        String paymentType = intent.getStringExtra("paymentType");
        String phone = intent.getStringExtra("phone");


        // Set the retrieved data values to TextViews in the layout
        TextView serviceNameTextView = findViewById(R.id.notification1578);
        TextView vehicleNameTextView = findViewById(R.id.notification15);
        TextView vehicleNumberTextView = findViewById(R.id.notification157);
        TextView addressTextView = findViewById(R.id.notification15782);
        TextView phone2 = findViewById(R.id.notification15785656);


        TextView dateTextView = findViewById(R.id.notification15785);
        paymenttypeTextView = findViewById(R.id.notification157856);
        TextView paymentstatusTextView = findViewById(R.id.notification1578565);
        // Find other TextViews similarly...

        serviceNameTextView.setText(serviceName);
        vehicleNameTextView.setText(vehicleName);
        addressTextView.setText(address);
        vehicleNumberTextView.setText(vehicleNumber);
        dateTextView.setText(dateTime);

        paymenttypeTextView.setText(paymentType);


        if (paymentSuccess!=null){
            paymentstatusTextView.setText(paymentSuccess);
        }else {
            paymentstatusTextView.setText("Not Payed");
        }
        phone2.setText(phone);

    }
}