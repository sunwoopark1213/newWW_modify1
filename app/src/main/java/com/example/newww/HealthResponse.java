package com.example.newww;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HealthResponse {
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("timelines")
        private List<Timeline> timelines;

        public List<Timeline> getTimelines() {
            return timelines;
        }
    }

    public static class Timeline {
        @SerializedName("intervals")
        private List<Interval> intervals;

        public List<Interval> getIntervals() {
            return intervals;
        }
    }

    public static class Interval {
        @SerializedName("startTime")
        private String startTime;

        @SerializedName("values")
        private Values values;

        public String getStartTime() {
            return startTime;
        }

        public Values getValues() {
            return values;
        }
    }

    public static class Values {
        @SerializedName("pollenCount")
        private double pollenCount;

        @SerializedName("uvIndex")
        private double uvIndex;

        @SerializedName("airQualityIndex")
        private double airQualityIndex;

        public double getPollenCount() {
            return pollenCount;
        }

        public double getUvIndex() {
            return uvIndex;
        }

        public double getAirQualityIndex() {
            return airQualityIndex;
        }
    }
}
