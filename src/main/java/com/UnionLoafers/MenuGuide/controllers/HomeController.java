package com.UnionLoafers.MenuGuide.controllers;

import com.UnionLoafers.MenuGuide.data.ItemCategoryRepository;
import com.UnionLoafers.MenuGuide.models.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

  @GetMapping
  public String index() {
    return "index";
  }

  @Autowired
  private ItemCategoryRepository itemCategoryRepository;

  @GetMapping("/")
  public String index(Model model) {
    List<ItemCategory> categories = (List<ItemCategory>) itemCategoryRepository.findAll();
    model.addAttribute("categories", categories);
    return "index";
  }
}