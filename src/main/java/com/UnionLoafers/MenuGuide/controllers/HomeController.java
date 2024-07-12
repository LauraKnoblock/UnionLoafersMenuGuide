package com.UnionLoafers.MenuGuide.controllers;

import com.UnionLoafers.MenuGuide.data.ItemCategoryRepository;
import com.UnionLoafers.MenuGuide.models.ItemCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

  @GetMapping
  public String index() {
    return "index";
  }

  @Autowired
  private ItemCategoryRepository itemCategoryRepository;

  private final String apiKey = "04f60a355e869a792188146b65b2281e";

  @GetMapping("/")
  public String index(Model model) throws IOException, InterruptedException {
    List<ItemCategory> categories = (List<ItemCategory>) itemCategoryRepository.findAll();

    model.addAttribute("categories", categories);
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openweathermap.org/data/3.0/onecall?lat=38&lon=-90&units=imperial&appid=" + apiKey))
            .header("OpenWeatherMap", "https://api.openweathermap.org")
            .header("openWeatherAPI-Key", "04f60a355e869a792188146b65b2281e")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

    HttpResponse<String> response = null;

    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> weatherData = mapper.readValue(response.body(), Map.class);
      model.addAttribute("weatherData", weatherData);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(response.body());

    return "index";
  }
}