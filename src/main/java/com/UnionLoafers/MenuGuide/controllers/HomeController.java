package com.UnionLoafers.MenuGuide.controllers;

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
}