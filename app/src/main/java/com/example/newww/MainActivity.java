package com.example.newww;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView cityName, temperature, minMaxTemp, weatherDescription;
    private ImageView weatherImage;
    private Button btnRecommend, btnBloom, btnSatellite, btnHealth, btnStorm, btnCalendar;
    private double tempInCelsius;  // 현재 온도를 저장할 변수 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.tvCityName);
        temperature = findViewById(R.id.tvTemperature);
        minMaxTemp = findViewById(R.id.tvMinMaxTemp);
        weatherDescription = findViewById(R.id.weatherDescription);
        weatherImage = findViewById(R.id.weatherImage);
        btnRecommend = findViewById(R.id.Recommend);
        btnBloom = findViewById(R.id.open_flower);
        btnSatellite = findViewById(R.id.satellite);
        btnHealth = findViewById(R.id.health);
        btnStorm = findViewById(R.id.storm);
        btnCalendar = findViewById(R.id.calendar);

        // Fetch weather data
        fetchWeatherData();

        btnRecommend.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
            intent.putExtra("CURRENT_TEMP", (float) tempInCelsius);
            startActivity(intent);
        });

        btnBloom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BloomActivity.class);
            startActivity(intent);
        });

        btnSatellite.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SatelliteActivity.class);
            startActivity(intent);
        });

        btnHealth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HealthActivity.class);
            startActivity(intent);
        });


        btnCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }

    private void fetchWeatherData() {
        WeatherService service = RetrofitClient.getRetrofitInstance().create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(37.5665, 126.9780, "e5817f21498336747d014b4242489de9");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    cityName.setText("도시: " + weatherResponse.name);
                    tempInCelsius = weatherResponse.main.temp - 273.15;
                    temperature.setText("온도: " + String.format("%.2f", tempInCelsius) + "°C");

                    // 최저 및 최고 온도를 켈빈에서 섭씨로 변환
                    double minTempInCelsius = weatherResponse.main.temp_min - 273.15;
                    double maxTempInCelsius = weatherResponse.main.temp_max - 273.15;

                    minMaxTemp.setText("최저: " + String.format("%.2f", minTempInCelsius) + "°C, 최고: " + String.format("%.2f", maxTempInCelsius) + "°C");
                    weatherDescription.setText(weatherResponse.weather.get(0).description);

                    switch (weatherResponse.weather.get(0).main.toLowerCase()) {
                        case "clouds":
                            weatherImage.setImageResource(R.drawable.cloudy);
                            break;
                        case "clear":
                            weatherImage.setImageResource(R.drawable.sunny);
                            break;
                        case "rain":
                            weatherImage.setImageResource(R.drawable.rainy);
                            break;
                        case "snow":
                            weatherImage.setImageResource(R.drawable.snowy);
                            break;
                        default:
                            weatherImage.setImageResource(R.drawable.ic_launcher_background);
                            break;
                    }
                } else {
                    Log.e(TAG, "응답 실패: " + response.message());
                    cityName.setText("데이터를 불러오는데 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, "요청 실패: ", t);
                cityName.setText("데이터를 불러오는데 실패했습니다.");
            }
        });
    }
}
