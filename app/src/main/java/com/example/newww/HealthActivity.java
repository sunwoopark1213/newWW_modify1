package com.example.newww;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HealthActivity extends AppCompatActivity {

    private TextView pollenTextView, uvTextView, airQualityTextView;
    private static final String BASE_URL = "https://api.tomorrow.io/";
    private static final String API_KEY = "RzZY79YQDmlsM5mWP6B8phKLNp1OLs0s";
    private static final String TAG = "HealthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        pollenTextView = findViewById(R.id.pollenTextView);
        uvTextView = findViewById(R.id.uvTextView);
        airQualityTextView = findViewById(R.id.airQualityTextView);

        fetchHealthData();
    }

    private void fetchHealthData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HealthApi healthApi = retrofit.create(HealthApi.class);

        // Define the time range for the request
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        String startTime = sdf.format(new Date());
        String endTime = sdf.format(new Date(System.currentTimeMillis() + 3600000L * 24)); // 24 hours ahead

        // Fields we want to query
        String fields = "pollenCount,uvIndex,airQualityIndex";
        String timesteps = "1h"; // hourly data

        Call<HealthResponse> call = healthApi.getHealthData(API_KEY, "37.5665,126.9780", fields, timesteps, startTime, endTime);

        call.enqueue(new Callback<HealthResponse>() {
            @Override
            public void onResponse(Call<HealthResponse> call, Response<HealthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API Response: " + response.body());
                    HealthResponse healthResponse = response.body();
                    if (healthResponse.getData() != null &&
                            healthResponse.getData().getTimelines() != null &&
                            !healthResponse.getData().getTimelines().isEmpty() &&
                            healthResponse.getData().getTimelines().get(0).getIntervals() != null &&
                            !healthResponse.getData().getTimelines().get(0).getIntervals().isEmpty()) {

                        HealthResponse.Interval interval = healthResponse.getData().getTimelines().get(0).getIntervals().get(0);

                        pollenTextView.setText("Pollen Count: " + interval.getValues().getPollenCount());
                        uvTextView.setText("UV Index: " + interval.getValues().getUvIndex());
                        airQualityTextView.setText("Air Quality Index: " + interval.getValues().getAirQualityIndex());
                    }
                } else {
                    Log.e(TAG, "API Response Error: " + response.message());
                    Log.e(TAG, "Response Body: " + response.errorBody());
                    Toast.makeText(HealthActivity.this, "Failed to load health data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HealthResponse> call, Throwable t) {
                Log.e(TAG, "API Call Failure: ", t);
                Toast.makeText(HealthActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private interface HealthApi {
        @GET("v4/timelines")
        Call<HealthResponse> getHealthData(
                @Query("apikey") String apiKey,
                @Query("location") String location,
                @Query("fields") String fields,
                @Query("timesteps") String timesteps,
                @Query("startTime") String startTime,
                @Query("endTime") String endTime
        );
    }
}
