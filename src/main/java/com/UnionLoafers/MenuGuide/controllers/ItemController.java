package com.UnionLoafers.MenuGuide.controllers;


import com.UnionLoafers.MenuGuide.data.ItemData;
import com.UnionLoafers.MenuGuide.models.Item;
import com.UnionLoafers.MenuGuide.models.ItemType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
  public String displayCreateItemForm(Model model) {
    model.addAttribute("title", "Create Item");
    model.addAttribute(new Item());
    model.addAttribute("types", ItemType.values());
    return "items/create";
  }

  @PostMapping("create")
  public String processCreateItemForm(@ModelAttribute Item newItem) {
    ItemData.add(newItem);
    return "redirect:/items";
  }

  @GetMapping("delete")
  public String displayDeleteItemForm(Model model) {
    model.addAttribute("title", "Delete Items");
    model.addAttribute("items", ItemData.getAll());
    return "items/delete";
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

  @GetMapping("edit/{itemId}")
  public String displayEditForm(Model model, @PathVariable int itemId){
    Item itemToEdit = ItemData.getById(itemId);

    model.addAttribute("item", itemToEdit);
    model.addAttribute("itemId", itemId);

    String title = "Edit Item " + itemToEdit.getName() + " (id=" + itemToEdit.getId() + ")";
    model.addAttribute("title", title);

    return "items/edit";
  }

  @PostMapping("edit/{id}")
  public String processEditForm(int id, String name, String desc) {
    Item itemToEdit = ItemData.getById(id);
    if (itemToEdit != null) {
      itemToEdit.setName(name);
      itemToEdit.setDesc(desc);
    }
    return "redirect:/items";
  }
}
