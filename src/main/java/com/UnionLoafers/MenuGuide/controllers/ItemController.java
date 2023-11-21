package com.UnionLoafers.MenuGuide.controllers;


import com.UnionLoafers.MenuGuide.data.ItemData;
import com.UnionLoafers.MenuGuide.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("items")
public class ItemController {


  @GetMapping
  public String  displayAllItems(Model model) {
    model.addAttribute("title", "All Items");
    model.addAttribute("items", ItemData.getAll());
            return "items/index";
  }
  @GetMapping("create")
  public String renderCreateItem() {
    return "items/create";
  }

  @PostMapping("create")
  public String createItem(@RequestParam String itemName, @RequestParam String itemDesc) {

    ItemData.add(new Item(itemName, itemDesc));
    return "redirect:/items";
  }

  @GetMapping("/delete")
  public String displayDeleteItemForm(Model model) {
    model.addAttribute("title", "Delete Items");
    model.addAttribute("items", ItemData.getAll());
    return "/items/delete";
  }

  @PostMapping("delete")
  public String processDeleteItemForm(@RequestParam(required = false) int[] itemIds) {
    if (itemIds != null) {
    for (int id: itemIds) {
      ItemData.remove(id);
      }
    }
    return "redirect:/items";
  }
}
