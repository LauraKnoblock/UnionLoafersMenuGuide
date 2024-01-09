package com.UnionLoafers.MenuGuide.controllers;


import com.UnionLoafers.MenuGuide.data.ItemCategoryRepository;
import com.UnionLoafers.MenuGuide.data.ItemData;
import com.UnionLoafers.MenuGuide.data.ItemRepository;
import com.UnionLoafers.MenuGuide.models.Item;
import com.UnionLoafers.MenuGuide.models.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("items")
public class ItemController {
  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemCategoryRepository itemCategoryRepository;
// findAll, save, findById
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
        return "items/index"; // Return early if invalid category ID
      } else {
        ItemCategory category = result.get();
        model.addAttribute("title", "Items in category: " + category.getName());
        model.addAttribute("items", category.getItems());
        return "items/index"; // Return early for category view
      }
    }

    model.addAttribute("items", items);
    return "items/index";
  }
  @GetMapping("create")
  public String displayCreateItemForm(Model model) {
    model.addAttribute("title", "Create Item");
    model.addAttribute(new Item());
    model.addAttribute("categories", itemCategoryRepository.findAll());
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
  public String processDeleteItemForm(@PathVariable int itemId) {
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

    }
      return "items/edit";
    }



  @PostMapping("edit/{id}")
  public String processEditForm(@PathVariable("id") int id, String name, String desc, String ingredients, String image, String vegan) {
    Optional<Item> optionalItem = itemRepository.findById(id);

    if (optionalItem.isPresent()) {
      Item itemToEdit = optionalItem.get();
      itemToEdit.setName(name);
      itemToEdit.setDesc(desc);
      itemToEdit.setImage(image);
      itemToEdit.setIngredients(ingredients);
      itemToEdit.setVegan(vegan != null ? vegan.trim() : null);

      // Save the changes back to the database
      itemRepository.save(itemToEdit);
    }

    return "redirect:/items";
  }
}
