package com.UnionLoafers.MenuGuide.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

  private final String apiKey = "04f60a355e869a792188146b65b2281e";

  public Map<String, Object> fetchWeatherData() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openweathermap.org/data/3.0/onecall?lat=38&lon=-90&units=imperial&appid=" + apiKey))
            .header("OpenWeatherMap", "https://api.openweathermap.org")
            .header("openWeatherAPI-Key", apiKey)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(response.body(), Map.class);
  }
}
