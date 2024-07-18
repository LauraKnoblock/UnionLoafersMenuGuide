package com.UnionLoafers.MenuGuide.controllers;

import com.UnionLoafers.MenuGuide.data.ItemCategoryRepository;
import com.UnionLoafers.MenuGuide.models.ItemCategory;
import com.UnionLoafers.MenuGuide.models.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.HashMap;
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
  @Autowired
  private WeatherService weatherService;


  @GetMapping("/")
  public String index(Model model) throws IOException, InterruptedException {
    List<ItemCategory> categories = (List<ItemCategory>) itemCategoryRepository.findAll();

    model.addAttribute("categories", categories);
    try {
      Map<String, Object> weatherData = weatherService.fetchWeatherData();
      model.addAttribute("weatherData", weatherData);
    } catch (IOException | InterruptedException e) {
      model.addAttribute("weatherData", new HashMap<>());
      e.printStackTrace();
    }


    return "index";
  }
}