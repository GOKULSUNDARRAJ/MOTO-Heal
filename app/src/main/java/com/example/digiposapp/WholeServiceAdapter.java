package com.example.digiposapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class WholeServiceAdapter extends RecyclerView.Adapter<WholeServiceAdapter.WholeServiceViewHolder> {

    private List<WholeService> wholeServiceList;

    public WholeServiceAdapter(List<WholeService> wholeServiceList) {
        this.wholeServiceList = wholeServiceList;
    }

    public void setWholeServiceList(List<WholeService> wholeServiceList) {
        this.wholeServiceList = wholeServiceList;
        notifyDataSetChanged(); // Notify RecyclerView to update data
    }

    @NonNull
    @Override
    public WholeServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_whole_service, parent, false);
        return new WholeServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WholeServiceViewHolder holder, int position) {
        WholeService service = wholeServiceList.get(position);
        holder.bind(wholeServiceList, service, position); // Pass the wholeServiceList and position
    }


    @Override
    public int getItemCount() {
        return wholeServiceList.size();
    }

    public static class WholeServiceViewHolder extends RecyclerView.ViewHolder {

        private TextView NameTextView;
        private TextView Numbervehical;
        private TextView notification1578,time;
        private ImageView vehicleImageView;
        private CardView cardproduct;
        private int currentPosition;
        private List<WholeService> wholeServiceList; // Instance variable

        public WholeServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTextView = itemView.findViewById(R.id.notification15);
            Numbervehical = itemView.findViewById(R.id.notification157);
            vehicleImageView = itemView.findViewById(R.id.image);
            notification1578 = itemView.findViewById(R.id.notification1578);
            cardproduct = itemView.findViewById(R.id.carrdproduct);
            time=itemView.findViewById(R.id.notification55);

            // Set click listener on the cardproduct (CardView) to handle item click
            cardproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentPosition != RecyclerView.NO_POSITION && wholeServiceList != null) {
                        WholeService service = wholeServiceList.get(currentPosition);
                        // Get the context from the view to start a new activity
                        // Inside onClick method of cardproduct click listener in WholeServiceViewHolder
                        Intent intent = new Intent(view.getContext(), DetailsActivity.class);


                        String serviceName = service.getServiceName();
                        String vehicleName = service.getVehicleName();
                        String vehicleNumber = service.getVehicleNumber();
                        String vehicleImage = service.getVehicleImage();
                        String address = service.getAddress(); // Assuming you have a getAddress() method in WholeService
                        String dateTime = service.getDateTime(); // Assuming you have a getDateTime() method in WholeService
                        String paymentSuccess = service.getPaymentSuccess(); // Assuming you have a getPaymentSuccess() method in WholeService
                        String paymentType = service.getPaymentType(); // Assuming you have a getPaymentType() method in WholeService

                        intent.putExtra("serviceName", serviceName);
                        intent.putExtra("vehicleName", vehicleName);
                        intent.putExtra("vehicleNumber", vehicleNumber);
                        intent.putExtra("vehicleImage", vehicleImage);
                        intent.putExtra("address", address);
                        intent.putExtra("dateTime", dateTime);
                        intent.putExtra("paymentSuccess", paymentSuccess);
                        intent.putExtra("paymentType", paymentType);
                        intent.putExtra("phone", service.getPhone());


                        view.getContext().startActivity(intent);

                    }
                }
            });
        }

        public void bind(List<WholeService> wholeServiceList, WholeService service, int position) {
            this.wholeServiceList = wholeServiceList; // Set the list reference
            currentPosition = position; // Set the current position
            NameTextView.setText(service.getVehicleName());
            Numbervehical.setText(service.getVehicleNumber());
            notification1578.setText(service.getServiceName());
            Picasso.get().load(service.getVehicleImage()).into(vehicleImageView);
            time.setText(service.getDateTime());
        }
    }


}
