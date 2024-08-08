package com.example.digiposapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private List<Service> serviceList;
    private String vehicleName, vehicleNumber, vehicleImage,address;

    public ServiceAdapter(List<Service> serviceList, String vehicleName, String vehicleNumber, String vehicleImage, String address) {
        this.serviceList = serviceList;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleImage = vehicleImage;
        this.address = address;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get the service at the current position
        Service service = serviceList.get(position);

        // Bind data to the ViewHolder views
        holder.serviceNameTextView.setText(service.getServiceName());


        Picasso.get().load(service.getImage()).into(holder.profile_image);

        // Set onClickListener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the service name from the TextView

                Drawable drawable = ContextCompat.getDrawable(view.getContext(), R.drawable.blackline2);

// Set the drawable as the background for profile_image
                holder.profile_image.setBackground(drawable);
                String serviceName = serviceList.get(position).getServiceName();


                // Start DetailActivity and pass the service name, vehicle name, vehicle number, and vehicle image as extras
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("service_name", serviceName);
                intent.putExtra("vehicle_name", vehicleName);
                intent.putExtra("vehicle_number", vehicleNumber);
                intent.putExtra("vehicle_image", vehicleImage);
                intent.putExtra("address", address);
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).finish();



            }
        });
    }


    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceNameTextView;
        LinearLayout cardproduct;
        ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.textView154567jh);
            cardproduct = itemView.findViewById(R.id.cardproduct);
            profile_image=itemView.findViewById(R.id.profile_image);
        }
    }
}
