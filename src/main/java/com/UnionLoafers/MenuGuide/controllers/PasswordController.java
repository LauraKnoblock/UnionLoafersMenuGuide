package com.UnionLoafers.MenuGuide.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

  private final String secretPassword;

  @Autowired
  public PasswordController(Environment env) {
    this.secretPassword = env.getProperty("secret.password");
  }


  @GetMapping("/password-entry")
  public String passwordEntry(@RequestParam String redirectUrl, Model model) {
    model.addAttribute("redirectUrl", redirectUrl);
    return "password-entry";
  }

  @PostMapping("/verify-password")
  public String verifyPassword(@RequestParam String password, @RequestParam String redirectUrl, Model model) {
    if (secretPassword.equals(password)) {
      return "redirect:" + redirectUrl;
    } else {
      model.addAttribute("error", true);
      model.addAttribute("redirectUrl", redirectUrl);
      return "password-entry";
    }
  }
}
