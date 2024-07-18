package com.UnionLoafers.MenuGuide.controllers;


import com.UnionLoafers.MenuGuide.data.ItemCategoryRepository;
import com.UnionLoafers.MenuGuide.data.ItemData;
import com.UnionLoafers.MenuGuide.data.ItemRepository;
import com.UnionLoafers.MenuGuide.models.Item;
import com.UnionLoafers.MenuGuide.models.ItemCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Controller
@RequestMapping("items")
public class ItemController {
  @Autowired
  private ItemRepository itemRepository;



  @Autowired
  private ItemCategoryRepository itemCategoryRepository;
// findAll, save, findById

  private final String apiKey = "04f60a355e869a792188146b65b2281e";


  @ModelAttribute("categories")
  public List<ItemCategory> addCategoriesToModel() {
    return (List<ItemCategory>) itemCategoryRepository.findAll();
  }

  @GetMapping("index")
  public String index(Model model) {
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
  @GetMapping
  public String displayAllItems(@RequestParam(required = false) Integer categoryId,
                                @RequestParam(required = false) String search,
                                Model model) {

    List<Item> items;

    if (categoryId == null) {
      model.addAttribute("title", "All Items");

      if (search != null && !search.isEmpty()) {
        // If search query is present, filter items based on the search query
        items = itemRepository.findItemsByNameContainingIgnoreCaseOrDescContainingIgnoreCaseOrIngredientsContainingIgnoreCase(search, search, search);
        model.addAttribute("title", "Search Results for: " + search);
      } else {
        // If no search query, get all items
        items = (List<Item>) itemRepository.findAll();
      }
    } else {
      Optional<ItemCategory> result = itemCategoryRepository.findById(categoryId);
      if (result.isEmpty()) {
        model.addAttribute("title", "Invalid Category ID: " + categoryId);
        items = Collections.emptyList(); // Set an empty list if category is invalid
      } else {
        ItemCategory category = result.get();
        model.addAttribute("title", "Items in category: " + category.getName());
        items = category.getItems();
      }
    }

    model.addAttribute("items", items);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openweathermap.org/data/3.0/onecall?lat=38&lon=-90&units=imperial&appid=" + apiKey))
            .header("OpenWeatherMap", "https://api.openweathermap.org")
            .header("openWeatherAPI-Key", apiKey)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

    HttpResponse<String> response = null;
    Map<String, Object> weatherData = new HashMap<>();

    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      ObjectMapper mapper = new ObjectMapper();
      weatherData = mapper.readValue(response.body(), Map.class);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    model.addAttribute("weatherData", weatherData);
    System.out.println(response.body());
    return "items/index";
  }

  @GetMapping("create")
  public String displayCreateItemForm(Model model) {
    model.addAttribute("title", "Create Item");
    model.addAttribute(new Item());
    model.addAttribute("categories", itemCategoryRepository.findAll());
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openweathermap.org/data/3.0/onecall?lat=38&lon=-90&units=imperial&appid=" + apiKey))
            .header("OpenWeatherMap", "https://api.openweathermap.org")
            .header("openWeatherAPI-Key", apiKey)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

    HttpResponse<String> response = null;
    Map<String, Object> weatherData = new HashMap<>();

    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      ObjectMapper mapper = new ObjectMapper();
      weatherData = mapper.readValue(response.body(), Map.class);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    model.addAttribute("weatherData", weatherData);
    System.out.println(response.body());
    return "items/create";
  }

  @PostMapping("create")
  public String processCreateItemForm(@ModelAttribute Item newItem) {

    itemRepository.save(newItem);
    return "redirect:/items";
  }

  @GetMapping("delete/{itemId}")
  public String displayDeleteItemForm(Model model, @PathVariable int itemId) {
    Optional<Item> optionalItem = itemRepository.findById(itemId);

    if (optionalItem.isPresent()) {
      Item itemToDelete = optionalItem.get();
      model.addAttribute("item", itemToDelete);
      model.addAttribute("title", "Delete Item " + itemToDelete.getName());
      return "items/delete";
    } else {
      // Handle the case where the item with the given ID is not found
      return "redirect:/items";
    }
  }

  @PostMapping("delete/{itemId}")
  public String processDeleteItemForm(@PathVariable int itemId, Model model) {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openweathermap.org/data/3.0/onecall?lat=38&lon=-90&units=imperial&appid=" + apiKey))
            .header("OpenWeatherMap", "https://api.openweathermap.org")
            .header("openWeatherAPI-Key", apiKey)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

    HttpResponse<String> response = null;
    Map<String, Object> weatherData = new HashMap<>();

    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      ObjectMapper mapper = new ObjectMapper();
      weatherData = mapper.readValue(response.body(), Map.class);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    model.addAttribute("weatherData", weatherData);
    System.out.println(response.body());
    itemRepository.deleteById(itemId);

    return "redirect:/items";
  }

  @GetMapping("edit/{itemId}")
  public String displayEditForm(Model model, @PathVariable int itemId) {
    Optional<Item> optionalItem = itemRepository.findById(itemId);
    List<ItemCategory> categories = new ArrayList<>((Collection) itemCategoryRepository.findAll());    if (optionalItem.isPresent()) {
      Item itemToEdit = optionalItem.get();

      model.addAttribute("item", itemToEdit);
      model.addAttribute("itemId", itemId);
      model.addAttribute("categories", categories);
      String title = "Edit Item " + itemToEdit.getName() + " (id=" + itemToEdit.getId() + ")";
      model.addAttribute("title", title);
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create("https://api.openweathermap.org/data/3.0/onecall?lat=38&lon=-90&units=imperial&appid=" + apiKey))
              .header("OpenWeatherMap", "https://api.openweathermap.org")
              .header("openWeatherAPI-Key", apiKey)
              .method("GET", HttpRequest.BodyPublishers.noBody())
              .build();

      HttpResponse<String> response = null;
      Map<String, Object> weatherData = new HashMap<>();

      try {
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        weatherData = mapper.readValue(response.body(), Map.class);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }

      model.addAttribute("weatherData", weatherData);
      System.out.println(response.body());

    }
      return "items/edit";
    }



  @PostMapping("edit/{id}")
  public String processEditForm(@PathVariable("id") int id,
                                @RequestParam String name,
                                @RequestParam String desc,
                                @RequestParam String ingredients,
                                @RequestParam String image,
                                @RequestParam String vegan,
                                @RequestParam Integer itemCategoryId) {

    Optional<Item> optionalItem = itemRepository.findById(id);

    if (optionalItem.isPresent()) {
      Item itemToEdit = optionalItem.get();
      itemToEdit.setName(name);
      itemToEdit.setDesc(desc);
      itemToEdit.setImage(image);
      itemToEdit.setIngredients(ingredients);
      itemToEdit.setVegan(vegan != null ? vegan.trim() : null);

      // Update the item category if the selected itemCategoryId is not null
      if (itemCategoryId != null) {
        Optional<ItemCategory> optionalCategory = itemCategoryRepository.findById(itemCategoryId);
        optionalCategory.ifPresent(itemToEdit::setItemCategory);
      }

      // Save the changes back to the database
      itemRepository.save(itemToEdit);
    }

    return "redirect:/items";
  }

}
