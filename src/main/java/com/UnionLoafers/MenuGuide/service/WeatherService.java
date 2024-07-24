package com.UnionLoafers.MenuGuide.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class WeatherService {

  private final String apiKey = System.getenv("WEATHER_API_KEY");

  public Map<String, Object> getWeatherData() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openweathermap.org/data/3.0/onecall?lat=38&lon=-90&units=imperial&appid=" + apiKey))
            .GET()
            .build();

    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(response.body(), Map.class);
    } else {
      throw new IOException("Failed to fetch weather data");
    }
  }
}
