package org.hritik.weatherapp.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.hritik.weatherapp.Class.Config;
import org.hritik.weatherapp.Class.RetrofitClient;
import org.hritik.weatherapp.Class.WeatherResponse;
import org.hritik.weatherapp.Interface.WeatherApi;
import org.hritik.weatherapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hritik on 28/08/2024
 */

public class WeatherActivity extends AppCompatActivity {
    TextView tvCityName,tvTemperature,tvHumidity, tvCondition, tvWindSpeed,tvUv;
    EditText etCityName;
    ImageView fetchBtn,backBtn,refreshBtn;

    ProgressDialog progressDialog;
    FusedLocationProviderClient fusedLocationProviderClient;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        getLatitudeAndLongitude();

        fetchBtn.setOnClickListener(v->{
            if(!TextUtils.isEmpty(etCityName.getText().toString().trim())){
                progressDialog.show();
                fetchWeatherDataByCity(etCityName.getText().toString());
            }
            else{
                etCityName.setError("Enter City name");
                etCityName.requestFocus();
            }
        });

        backBtn.setOnClickListener(v-> onBackPressed());
        refreshBtn.setOnClickListener(v->{
            progressDialog.show();
            if(TextUtils.isEmpty(etCityName.getText().toString().trim())){
                getLatitudeAndLongitude();
            }
            else{
                fetchWeatherDataByCity(etCityName.getText().toString());
            }

        });
    }


    private void initViews() {
        // Initialize and show progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching data");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        tvCityName = findViewById(R.id.tv_ll);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvCondition = findViewById(R.id.tv_condition);
        tvWindSpeed = findViewById(R.id.tv_windspeed);
        etCityName = findViewById(R.id.et_city);
        fetchBtn = findViewById(R.id.btn_fetch);
        backBtn = findViewById(R.id.btn_back);
        refreshBtn = findViewById(R.id.btn_refresh);
        tvUv = findViewById(R.id.tv_uv);
    }

    // Method to get Latitude and Longitude
    private void getLatitudeAndLongitude() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Location location = task.getResult();
                        latitude = location.getLatitude();
                        longitude = location.getLatitude();
                        tvCityName.setText(latitude+","+longitude);
                        fetchWeatherDataByLatLong(latitude,longitude);


                    } else {
                        etCityName.requestFocus();
                        progressDialog.dismiss();
                        Toast.makeText(WeatherActivity.this, "Location Unavailable", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    // Method to fetch weather data by Latitude and Longitude
    private void fetchWeatherDataByLatLong(double latitude, double longitude){
        Retrofit retrofit = RetrofitClient.getClient();
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        String location = latitude+","+longitude;
        Call<WeatherResponse> call = weatherApi.getWeatherByLocation(Config.API_KEY,
                location,"no");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    WeatherResponse weatherResponse = response.body();
                    //Update UI with weatherResponse;
                    updateUI(weatherResponse);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("mTag",t.getMessage());
                Toast.makeText(WeatherActivity.this, "Failed to fetch data: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to fetch weather data by city name
    private void fetchWeatherDataByCity(String cityName){
        Retrofit retrofit = RetrofitClient.getClient();
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        Call<WeatherResponse> call = weatherApi.getWeatherByLocation(Config.API_KEY,
                cityName,"no");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    WeatherResponse weatherResponse = response.body();
                    //Update UI with weatherResponse;
                    updateUI(weatherResponse);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(WeatherActivity.this, "Failed to fetch data: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // // Method to the update UI
    @SuppressLint("DefaultLocale")
    private void updateUI(WeatherResponse weatherResponse){
        String cityName = weatherResponse.getLocation().getName();
        String temperature = String.format("%.1f°C",weatherResponse.getCurrent().getTempC());
        String humidity = String.format("%d%%",weatherResponse.getCurrent().getHumidity());
        String condition = String.format("%s",weatherResponse.getCurrent().getCondition().getText());
        String windSpeed = String.format("%.1f kph",weatherResponse.getCurrent().getWindKph());
        String uvIndex = String.format("%.1f",weatherResponse.getCurrent().getUv());

        tvCityName.setText(cityName);
        tvTemperature.setText(temperature);
        tvHumidity.setText(humidity);
        tvCondition.setText(condition);
        tvWindSpeed.setText(windSpeed);
        tvUv.setText(uvIndex);
    }



}