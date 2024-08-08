package com.example.digiposapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener, LocationListener {

    private static final int MY_LOCATION_REQUEST_CODE = 123;
    private static final long LOCATION_UPDATE_INTERVAL = 10000; // Update interval in milliseconds
    private static final float LOCATION_UPDATE_DISTANCE = 10.0f; // Update distance in meters

    private GoogleMap mMap;
    private Geocoder geocoder;
    private LocationManager locationManager;
    private Location currentLocation;
    private boolean locationPermissionGranted = false;

    private ImageView locationButton,search;
    private EditText searchEditText;

    public interface AddressListener {
        void onAddressReceived(String address);
    }

    private AddressListener addressListener;

    public void setAddressListener(AddressListener listener) {
        this.addressListener = listener;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        geocoder = new Geocoder(requireContext(), Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchEditText = view.findViewById(R.id.productsearch);
        locationButton = view.findViewById(R.id.bottomButton);





        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchLocation(searchEditText.getText().toString());
            }
        });



        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (locationPermissionGranted) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationClickListener(this);

        // Disable default My Location button
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }

        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationPermissionGranted) {
            startLocationUpdates();
        }

        // Set up click listener for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Clear existing markers
                mMap.clear();
                // Add marker at the clicked position
                mMap.addMarker(new MarkerOptions().position(latLng).title("Clicked Location"));
                // Zoom to the clicked location
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                // Convert LatLng to Location
                Location clickedLocation = new Location("Clicked Location");
                clickedLocation.setLatitude(latLng.latitude);
                clickedLocation.setLongitude(latLng.longitude);
                // Pass clicked location to further processing if needed
                processClickedLocation(clickedLocation);
            }
        });
        searchLocation("Chennai");

    }

    private void processClickedLocation(Location clickedLocation) {
        convertLocationToAddress(clickedLocation);
        saveClickedLocation(clickedLocation);
    }

    @Override
    public void onMyLocationClick(@NonNull android.location.Location location) {
        currentLocation = location;
        showSizeIncreaseNotification();
        convertLocationToAddress(location);

        CustomDialogClassfoeadd4 cdd = new CustomDialogClassfoeadd4(getContext());
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();


    }

    private void convertLocationToAddress(android.location.Location location) {
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                StringBuilder addressStringBuilder = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressStringBuilder.append(address.getAddressLine(i)).append("\n");
                }
                String formattedAddress = addressStringBuilder.toString();
                if (addressListener != null) {
                    addressListener.onAddressReceived(formattedAddress);
                }
            } else {
                Toast.makeText(requireContext(), "Address not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Geocoder error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showSizeIncreaseNotification() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String currentTime = getCurrentTime();
        int smallIcon = R.drawable.baseline_electric_car_24;
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.lr);
        Bitmap largeImage = BitmapFactory.decodeResource(getResources(), R.drawable.lr);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "list_size_channel")
                .setSmallIcon(smallIcon)
                .setContentTitle("Moto Heal Service ")
                .setContentText("Please get new service with Moto Heal with new Offers" + " at " + currentTime)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(largeImage)
                        .bigLargeIcon(null));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        createNotificationChannel();
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ListSizeChannel";
            String description = "Channel for list size notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("list_size_channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_INTERVAL,
                    LOCATION_UPDATE_DISTANCE,
                    this);
        }
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                currentLocation = lastKnownLocation;
                pinLocationOnMap(currentLocation);
            } else {
                Toast.makeText(requireContext(), "Unable to retrieve location", Toast.LENGTH_SHORT).show();
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }
    }

    private void pinLocationOnMap(Location location) {
        if (mMap != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }

    private void saveClickedLocation(Location clickedLocation) {
        CustomDialogClassfoeadd4 cdd = new CustomDialogClassfoeadd4(getContext());
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("clicked_latitude", (float) clickedLocation.getLatitude());
        editor.putFloat("clicked_longitude", (float) clickedLocation.getLongitude());
        editor.apply();
        Toast.makeText(requireContext(), "Clicked location saved", Toast.LENGTH_SHORT).show();
    }

    private void searchLocation(String locationName) {
        if (!locationName.isEmpty()) {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
                if (addressList != null && !addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.clear(); // Clear existing markers
                    mMap.addMarker(new MarkerOptions().position(latLng).title(locationName));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error searching location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Please enter a location", Toast.LENGTH_SHORT).show();
        }
    }

}
