package com.UnionLoafers.MenuGuide.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("items")
public class ItemController {

  @GetMapping
  public String  displayAllItems(Model model) {
    List<String> items = new ArrayList<>();
    items.add("Little Gem Salad");
    model.addAttribute("items", items);
            return "items/index";
  }
  @GetMapping("create")
  public String renderCreateItem() {
    return "items/create";
  }
}
