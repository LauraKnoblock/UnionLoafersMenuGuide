package com.UnionLoafers.MenuGuide.controllers;


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

  private static List<String> items = new ArrayList<>();

  @GetMapping
  public String  displayAllItems(Model model) {
    model.addAttribute("items", items);
            return "items/index";
  }
  @GetMapping("create")
  public String renderCreateItem() {
    return "items/create";
  }

  @PostMapping("create")
  public String createItem(@RequestParam String itemName) {
    items.add(itemName);
    return "redirect:/items";
  }
}
