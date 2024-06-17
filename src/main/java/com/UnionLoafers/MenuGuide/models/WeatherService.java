package com.UnionLoafers.MenuGuide.models;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WeatherService {
  private static final String API_KEY = "04f60a355e869a792188146b65b2281e";
  private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";

  private OkHttpClient client;

  public WeatherService() {
    this.client = new OkHttpClient();
  }

  public String getWeather(double lat, double lon) throws Exception {
    String url = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";

    Request request = new Request.Builder()
            .url(url)
            .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

      return response.body().string();
    }
  }
}

