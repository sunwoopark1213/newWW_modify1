package com.example.newww;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SatelliteActivity extends AppCompatActivity {

    private ImageView satelliteImageView;
    private static final String KMA_BASE_URL = "http://apis.data.go.kr/";
    private static final String API_KEY = "W9ze7mNher3BNHwygQAg3kyTnIPMp/QBIOspspU246y5DcHCcQ82k3taO6o8rD8ymUf/k5CcRwRau9jX9R1xwQ==";
    private static final String TAG = "SatelliteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satellite);

        satelliteImageView = findViewById(R.id.satelliteImageView);

        fetchSatelliteImage();
    }

    private void fetchSatelliteImage() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(KMA_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KmaApi kmaApi = retrofit.create(KmaApi.class);
        Call<KmaResponse> call = kmaApi.getSatelliteImage(API_KEY);

        call.enqueue(new Callback<KmaResponse>() {
            @Override
            public void onResponse(Call<KmaResponse> call, Response<KmaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String imageUrl = response.body().getImageUrl();
                    Log.d(TAG, "Image URL: " + imageUrl);
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(SatelliteActivity.this)
                                .load(imageUrl)
                                .into(satelliteImageView);
                    } else {
                        Toast.makeText(SatelliteActivity.this, "No image URL returned", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "API Response Error: " + response.message());
                    Toast.makeText(SatelliteActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KmaResponse> call, Throwable t) {
                Log.e(TAG, "API Call Failure: ", t);
                Toast.makeText(SatelliteActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private interface KmaApi {
        @GET("1360000/SatelliteImageService/getSatelliteImageList")
        Call<KmaResponse> getSatelliteImage(@Query("serviceKey") String serviceKey);
    }

    private static class KmaResponse {
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
