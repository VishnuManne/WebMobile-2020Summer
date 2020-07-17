package com.example.androidhardware;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    //Declaring the variables

    private GoogleMap mMap;
    private static final String TAG = "MainActivity";
    private boolean mLocationPermission = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        checkLocationPermission();
    }

    /*
     * This method will call after Map is ready.
     * In this we will call the current location of the device.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermission) {
            getCurrentLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    /*
     * This method will call once the user select the permission either allow or deny.
     * Once the permission is allow then we are initializing the map.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermission = false;
        switch (requestCode) {
            case 2345:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermission = true;
                    initMap();
                }
        }
    }

    /*
     * In this method we will initialize the map settings.
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /*
     * In this method we will check the location permission.
     */
    private void checkLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermission = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 2345);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, 2345);
        }
    }

    /*
     * In this method we will get the current location details.
     */
    private void getCurrentLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermission){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Log.i(TAG,"Location found");
                            Location currentLocation = (Location) task.getResult();
                            updateLocation(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),15f);
                        } else{
                            Log.e(TAG,"Location not found");
                        }
                    }
                });
            }
        }catch (SecurityException e)
        {
            Log.e(TAG,e.getMessage());
        }
    }

    /*
     * In this method we will update the marker and latitude and longitude details.
     */
    private void updateLocation(LatLng latlng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));
        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(latlng).title("Latitude : "+latlng.latitude+" "+"Longitude : "+latlng.longitude));
    }
}