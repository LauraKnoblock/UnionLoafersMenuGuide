package com.UnionLoafers.MenuGuide.controllers;


import com.UnionLoafers.MenuGuide.data.ItemCategoryRepository;
import com.UnionLoafers.MenuGuide.data.ItemRepository;
import com.UnionLoafers.MenuGuide.models.Item;
import com.UnionLoafers.MenuGuide.models.ItemCategory;
import com.UnionLoafers.MenuGuide.models.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.*;

@Controller
@RequestMapping("items")
public class ItemController {

  @Autowired
  private WeatherService weatherService;


  @Autowired
  private ItemRepository itemRepository;



  @Autowired
  private ItemCategoryRepository itemCategoryRepository;



  @ModelAttribute("categories")
  public List<ItemCategory> addCategoriesToModel() {
    return (List<ItemCategory>) itemCategoryRepository.findAll();
  }

  @GetMapping("index")
  public String index(Model model) {
    List<ItemCategory> categories = (List<ItemCategory>) itemCategoryRepository.findAll();
    model.addAttribute("categories", categories);

    try {
      Map<String, Object> weatherData = weatherService.fetchWeatherData();
      model.addAttribute("weatherData", weatherData);
    }
    catch (IOException | InterruptedException e) {
      model.addAttribute("weatherData", new HashMap<>());
      e.printStackTrace();
    }
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
        items = itemRepository.findItemsByNameContainingIgnoreCaseOrDescContainingIgnoreCaseOrIngredientsContainingIgnoreCase(search, search, search);
        model.addAttribute("title", "Search Results for: " + search);
      } else {
        items = (List<Item>) itemRepository.findAll();
      }
    } else {
      Optional<ItemCategory> result = itemCategoryRepository.findById(categoryId);
      if (result.isEmpty()) {
        model.addAttribute("title", "Invalid Category ID: " + categoryId);
        items = Collections.emptyList();
      } else {
        ItemCategory category = result.get();
        model.addAttribute("title", "Items in " + category.getName() + " Category");
        items = category.getItems();
      }
    }

    model.addAttribute("items", items);

    try {
      Map<String, Object> weatherData = weatherService.fetchWeatherData();
      model.addAttribute("weatherData", weatherData);
    } catch (IOException | InterruptedException e) {
      model.addAttribute("weatherData", new HashMap<>());
      e.printStackTrace();
    }

    return "items/index";
  }

  @GetMapping("create")
  public String displayCreateItemForm(Model model) {
    model.addAttribute("title", "Create Item");
    model.addAttribute(new Item());
    model.addAttribute("categories", itemCategoryRepository.findAll());
    try {
      Map<String, Object> weatherData = weatherService.fetchWeatherData();
      model.addAttribute("weatherData", weatherData);
    }
    catch (IOException | InterruptedException e) {
      model.addAttribute("weatherData", new HashMap<>());
      e.printStackTrace();
    }
    return "items/create";
  }

  @PostMapping("create")
  public String processCreateItemForm(@ModelAttribute Item newItem, Model model) {
    if (newItem.getItemCategory() == null || newItem.getItemCategory().isEmpty()
            || newItem.getItemCategory().equals("Choose a Category...")) {
      model.addAttribute("categoryError", "Please choose a category for the item.");
      return "redirect:/items/create";
    }
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
      return "redirect:/items";
    }
  }

  @PostMapping("delete/{itemId}")
  public String processDeleteItemForm(@PathVariable int itemId, Model model) {
    try {
      Map<String, Object> weatherData = weatherService.fetchWeatherData();
      model.addAttribute("weatherData", weatherData);
    }
    catch (IOException | InterruptedException e) {
      model.addAttribute("weatherData", new HashMap<>());
      e.printStackTrace();
    }
    itemRepository.deleteById(itemId);

    return "redirect:/items";
  }

  @GetMapping("edit/{itemId}")
  public String displayEditForm(Model model, @PathVariable int itemId) {
    Optional<Item> optionalItem = itemRepository.findById(itemId);
    List<ItemCategory> categories = new ArrayList<>((Collection) itemCategoryRepository.findAll());
    if (optionalItem.isPresent()) {
      Item itemToEdit = optionalItem.get();

      model.addAttribute("item", itemToEdit);
//      model.addAttribute("itemId", itemId);
      model.addAttribute("categories", categories);
      String title = "Edit Item " + itemToEdit.getName();
      model.addAttribute("title", title);

      try {
        Map<String, Object> weatherData = weatherService.fetchWeatherData();
        model.addAttribute("weatherData", weatherData);
      }
      catch (IOException | InterruptedException e) {
        model.addAttribute("weatherData", new HashMap<>());
        e.printStackTrace();
      }

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
      itemToEdit.setVegan(vegan);

      if (itemCategoryId != null) {
        Optional<ItemCategory> optionalCategory = itemCategoryRepository.findById(itemCategoryId);
        if (optionalCategory.isPresent()) {
          ItemCategory category = optionalCategory.get();
          itemToEdit.setItemCategory(category);
        }
      }

      itemRepository.save(itemToEdit);
    }

    return "redirect:/items";
  }

}
