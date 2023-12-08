package com.UnionLoafers.MenuGuide.controllers;


import com.UnionLoafers.MenuGuide.data.ItemData;
import com.UnionLoafers.MenuGuide.data.ItemRepository;
import com.UnionLoafers.MenuGuide.models.Item;
import com.UnionLoafers.MenuGuide.models.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("items")
public class ItemController {
  @Autowired
  private ItemRepository itemRepository;
// findAll, save, findById
  @GetMapping
  public String  displayAllItems(Model model) {
    model.addAttribute("title", "All Items");
    model.addAttribute("items", itemRepository.findAll());
            return "items/index";
  }
  @GetMapping("create")
  public String displayCreateItemForm(Model model) {
    model.addAttribute("title", "Create Item");
    model.addAttribute(new Item());
    model.addAttribute("types", ItemType.values());
    return "items/create";
  }

  @PostMapping("create")
  public String processCreateItemForm(@ModelAttribute Item newItem) {
    itemRepository.save(newItem);
    return "redirect:/items";
  }

  @GetMapping("delete")
  public String displayDeleteItemForm(Model model) {
    model.addAttribute("title", "Delete Items");
    model.addAttribute("items", itemRepository.findAll());
    return "items/delete";
  }

  @PostMapping("delete")
  public String processDeleteItemForm(@RequestParam(required = false) int[] itemIds) {
    if (itemIds != null) {
    for (int id: itemIds) {
      itemRepository.deleteById(id);
      }
    }
    return "redirect:/items";
  }

  @GetMapping("edit/{itemId}")
  public String displayEditForm(Model model, @PathVariable int itemId) {
    Optional<Item> optionalItem = itemRepository.findById(itemId);

    if (optionalItem.isPresent()) {
      Item itemToEdit = optionalItem.get();

      model.addAttribute("item", itemToEdit);
      model.addAttribute("itemId", itemId);

      String title = "Edit Item " + itemToEdit.getName() + " (id=" + itemToEdit.getId() + ")";
      model.addAttribute("title", title);
    }
      return "items/edit";
    }



  @PostMapping("edit/{id}")
  public String processEditForm(@PathVariable("id") int id, String name, String desc) {
    Optional<Item> optionalItem = itemRepository.findById(id);

    if (optionalItem.isPresent()) {
      Item itemToEdit = optionalItem.get();
      itemToEdit.setName(name);
      itemToEdit.setDesc(desc);

      // Save the changes back to the database
      itemRepository.save(itemToEdit);
    }

    return "redirect:/items";
  }
}
