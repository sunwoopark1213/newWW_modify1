package com.example.newww;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalendarActivity extends AppCompatActivity {

    private GridView calendarGridView;
    private static final String BASE_URL = "https://api.tomorrow.io/";
    private static final String API_KEY = "RzZY79YQDmlsM5mWP6B8phKLNp1OLs0s";
    private static final String TAG = "CalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarGridView = findViewById(R.id.calendarGridView);

        fetchYearlyWeatherData();
    }

    private void fetchYearlyWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);

        // Define the time range for the request
        String startTime = "2024-01-01T00:00:00Z";
        String endTime = "2024-12-31T23:59:59Z";

        // Fields we want to query
        String fields = "weatherCode";
        String timesteps = "1d"; // daily data

        Call<YearlyWeatherResponse> call = weatherService.getYearlyWeatherData(API_KEY, "37.5665,126.9780", fields, timesteps, startTime, endTime);

        call.enqueue(new Callback<YearlyWeatherResponse>() {
            @Override
            public void onResponse(Call<YearlyWeatherResponse> call, Response<YearlyWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    YearlyWeatherResponse weatherResponse = response.body();
                    int[] weatherCodes = new int[365];
                    int index = 0;
                    for (YearlyWeatherResponse.Interval interval : weatherResponse.getData().getTimelines().get(0).getIntervals()) {
                        weatherCodes[index++] = interval.getValues().getWeatherCode();
                    }
                    CalendarAdapter adapter = new CalendarAdapter(CalendarActivity.this, weatherCodes);
                    calendarGridView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "API Response Error: " + response.message());
                    Toast.makeText(CalendarActivity.this, "Failed to load yearly weather data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YearlyWeatherResponse> call, Throwable t) {
                Log.e(TAG, "API Call Failure: ", t);
                Toast.makeText(CalendarActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
