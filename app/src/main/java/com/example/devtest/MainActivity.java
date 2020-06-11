package com.example.devtest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        getLocationUpdates();
    }

    public void getLocationUpdates(){
        if (ActivityCompat.checkSelfPermission((Activity)this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
        }

        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();

        locationRequest.setPriority((LocationRequest.PRIORITY_HIGH_ACCURACY));
        locationRequest.setFastestInterval(2000);
        locationRequest.setInterval(4000);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d("debug", Double.toString(locationResult.getLastLocation().getLatitude()) + "," +  Double.toString(locationResult.getLastLocation().getLongitude()));
            }
        }, getMainLooper());
    }
}