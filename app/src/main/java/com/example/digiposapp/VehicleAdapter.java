package com.example.digiposapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {
    private List<Vehicle> vehicleList;

    public VehicleAdapter(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.nameTextView.setText(vehicle.getName());
        holder.numberTextView.setText(vehicle.getNumber());



        Picasso.get().load(vehicle.getImage()).into(holder.categoryCoverUrlTextView);



        holder.categorycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the index position of the clicked item
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    // Retrieve the corresponding Vehicle object
                    Vehicle clickedVehicle = vehicleList.get(clickedPosition);
                    // Extract the name, number, and image URL
                    String clickedVehicleName = clickedVehicle.getName();
                    String clickedVehicleNumber = clickedVehicle.getNumber();
                    String clickedVehicleImage = clickedVehicle.getImage();

                    // Create an Intent to start ServicesActivity
                    Intent intent = new Intent(view.getContext(), ServicesActivity.class);
                    // Put the name, number, and image URL as extras in the Intent
                    intent.putExtra("vehicle_name", clickedVehicleName);
                    intent.putExtra("vehicle_number", clickedVehicleNumber);
                    intent.putExtra("vehicle_image", clickedVehicleImage);
                    intent.putExtra("address", vehicle.getAddress());

                    // Start the activity
                    view.getContext().startActivity(intent);
                }
            }
        });


        holder.categorycard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    // Retrieve the corresponding Vehicle object
                    Vehicle clickedVehicle = vehicleList.get(clickedPosition);
                    // Extract the name, number, and image URL
                    String clickedVehicleName = clickedVehicle.getName();
                    String clickedVehicleNumber = clickedVehicle.getNumber();
                    String clickedVehicleImage = clickedVehicle.getImage();

                    // Create an Intent to start ServicesActivity
                    Intent intent = new Intent(view.getContext(), VehicalDetailActivity.class);
                    // Put the name, number, and image URL as extras in the Intent
                    intent.putExtra("vehicle_name", clickedVehicleName);
                    intent.putExtra("vehicle_number", clickedVehicleNumber);
                    intent.putExtra("vehicle_image", clickedVehicleImage);
                    intent.putExtra("address", vehicle.getAddress());

                    // Start the activity
                    view.getContext().startActivity(intent);
                }

                return false;
            }
        });

    }




    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView numberTextView;
        androidx.constraintlayout.widget.ConstraintLayout categorycard;
        de.hdodenhof.circleimageview.CircleImageView categoryCoverUrlTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            categorycard=itemView.findViewById(R.id.categorycard);
            categoryCoverUrlTextView=itemView.findViewById(R.id.categoryCoverUrlTextView);


        }
    }
}


