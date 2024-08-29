package org.hritik.weatherapp.Class;

/**
 * Created by Hritik on 28/08/2024
 */

public class WeatherResponse {
    private Location location;
    private Current current;

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public static class Location{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Current{
        private double temp_c;
        private int humidity;
        private Condition condition;
        private Float wind_kph;
        private float uv;

        public Condition getCondition() {
            return condition;
        }

        public double getTempC() {
            return temp_c;
        }

        public int getHumidity() {
            return humidity;
        }


        public float getWindKph() {
            return wind_kph;
        }

        public float getUv() {
            return uv;
        }
    }

    public class Condition{
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
