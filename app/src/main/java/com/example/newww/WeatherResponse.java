package com.example.newww;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("name")
    public String name;

    @SerializedName("main")
    public Main main;

    @SerializedName("weather")
    public List<Weather> weather;

    public static class Main {
        @SerializedName("temp")
        public double temp;

        @SerializedName("temp_min")
        public double temp_min;

        @SerializedName("temp_max")
        public double temp_max;
    }

    public static class Weather {
        @SerializedName("main")
        public String main;

        @SerializedName("description")
        public String description;
    }
}
