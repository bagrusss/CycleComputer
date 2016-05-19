package ru.sudoteam.cyclecomputer.web;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by fatman on 18/05/16.
 */
public class WeatherRest {
    private final static String API_TEST_KEY = "qGJzWByA20mshZ4kZ6b0K4HYvwtXp1I0cDyjsnVrijSnuMeplH";
    private final static String LABEL_URL = "https://simple-weather.p.mashape.com/weather";
    private final static String JSON_URL = "https://simple-weather.p.mashape.com/weatherdata";

    private final static int COMFORT_TEMPRETURE_FAHRENHEIT = 59;

    public static boolean isGoodWeatherLabel(String latitude, String longitude) {
        String weather = checkWeather(LABEL_URL, latitude, longitude);
        return weather != null && weather.contains("Sun") && Integer.valueOf(weather.split(" ")[0] ) > COMFORT_TEMPRETURE_FAHRENHEIT;
    }

    public static boolean isGoodWeather(String latitude, String longitude) {
        String weather = checkWeather(JSON_URL, latitude, longitude);
        if (weather != null) {
            return true;
        }
        return false;
        //TODO check Json weather with GSON
    }

    private static String checkWeather(String url, String latitude, String longtitude) {
        InputStream is = null;
        try {
            final String uri = Uri.parse(url)
                    .buildUpon()
                    .appendQueryParameter("lat", latitude)
                    .appendQueryParameter("lng", longtitude)
                    .build()
                    .toString();
            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("X-Mashape-Key", API_TEST_KEY);
            conn.setRequestProperty("Accept", "text/plain");
            conn.connect();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();
            if (responseCode == 200) {
                return inputStreamToString(is);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String inputStreamToString(final InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
