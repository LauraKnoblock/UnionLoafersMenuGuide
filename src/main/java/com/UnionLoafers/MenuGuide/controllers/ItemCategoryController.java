package com.UnionLoafers.MenuGuide.controllers;


import com.UnionLoafers.MenuGuide.data.ItemCategoryRepository;
import com.UnionLoafers.MenuGuide.models.ItemCategory;
import com.UnionLoafers.MenuGuide.models.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("itemCategories")
public class ItemCategoryController {

  @Autowired
  private WeatherService weatherService;


  @Autowired
  private ItemCategoryRepository itemCategoryRepository;


  @GetMapping
  public String  displayAllItems(Model model) {
    model.addAttribute("title", "All Categories");
    model.addAttribute("categories", itemCategoryRepository.findAll());

    try {
      Map<String, Object> weatherData = weatherService.fetchWeatherData();
      model.addAttribute("weatherData", weatherData);
    }
    catch (IOException | InterruptedException e) {
      model.addAttribute("weatherData", new HashMap<>()); // Provide empty data or default values
      e.printStackTrace();
    }

    return "itemCategories/index";
  }



  @GetMapping("/create")
  public String displayCreateItemForm(Model model) {
    model.addAttribute("title", "Create Category");
    model.addAttribute(new ItemCategory());
    return "itemCategories/create";
  }

  @PostMapping("create")
  public String processCreateItemCategoryForm(@Valid @ModelAttribute ItemCategory itemCategory, Errors errors, Model model) {

    if (errors.hasErrors()) {
      model.addAttribute("title", "Create Category");
      model.addAttribute(new ItemCategory());
      return "itemCategories/create";
    }

    itemCategoryRepository.save(itemCategory);
    return "redirect:";
  }
}
