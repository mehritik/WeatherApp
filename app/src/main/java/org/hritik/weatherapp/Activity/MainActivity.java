package org.hritik.weatherapp.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.hritik.weatherapp.R;


/**
 * Created by Hritik on 28/08/2024
 */

public class MainActivity extends AppCompatActivity {
    //Request code for location permission
    private static final int LOCATION_PERMISSION_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // on click listener for button
        findViewById(R.id.btn_start).setOnClickListener(v->{
            if(isInternetConnected()){
                if(isPermissionAllowed()){
                    startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
                }
                else{
                    askPermission();
                }
            }
            else{
                Toast.makeText(this, "Please check network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // check if permission is allowed
    private boolean isPermissionAllowed(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    // ask permission for location services
    private void askPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_PERMISSION_REQ_CODE){
            if(grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                //Permission granted
                Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show();
            }
            else{
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(getApplicationContext(),WeatherActivity.class));
        }
    }

    // Method to check internet connection
    private boolean isInternetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.isConnected();
        }
        return false;
    }
}