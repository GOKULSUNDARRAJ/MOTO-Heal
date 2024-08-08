package com.example.digiposapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class DetailActivity extends AppCompatActivity {

    String vehicleName ,vehicleNumber,vehicleImage,serviceName ,address1;
    TextView name,number,type,address;

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Window window=getWindow();
        window.setBackgroundDrawableResource(R.drawable.button_bg);


        serviceName = getIntent().getStringExtra("service_name");

        name=findViewById(R.id.notification15);
        number=findViewById(R.id.notification157);
        type=findViewById(R.id.notification1578);
        image=findViewById(R.id.image);
        address=findViewById(R.id.notification15782);






        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("vehicle_name") && intent.hasExtra("vehicle_number") && intent.hasExtra("vehicle_image")) {
            vehicleName = intent.getStringExtra("vehicle_name");
            vehicleNumber = intent.getStringExtra("vehicle_number");
            vehicleImage = intent.getStringExtra("vehicle_image");
            address1=intent.getStringExtra("address");


            name.setText(vehicleName);
            number.setText(vehicleNumber);
            type.setText(serviceName);
            address.setText(address1);
            Glide.with(DetailActivity.this) // Use holder.itemView.getContext()
                    .load(vehicleImage)
                    .into(image);



        }

    }



    public void gotopayment1(View view) {
        Intent intent = new Intent(view.getContext(), PaymentActivity.class);
        intent.putExtra("vehicle_name", vehicleName);
        intent.putExtra("vehicle_number", vehicleNumber);
        intent.putExtra("vehicle_image", vehicleImage);
        intent.putExtra("address", address1);
        intent.putExtra("service_name", serviceName);
        view.getContext().startActivity(intent);
    }
}

