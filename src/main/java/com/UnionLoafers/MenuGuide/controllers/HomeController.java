package com.UnionLoafers.MenuGuide.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {


//  @ResponseBody
//  @GetMapping("hello")
//  public String hello() {
//    return "Hello, Barry & Pepper!";
//  }


  @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value="hello")
  @ResponseBody
  public String helloWithQueryParam(@RequestParam String name) {
    return "Hello, " + name + "!";
  }

  // handles requests of the form /hello/LaunchCode
  @GetMapping("hello/{name}")
  @ResponseBody
  public String helloWithPathParam(@PathVariable String name){
    return "Hello, " + name + "!";
  }

  @GetMapping("form")
  @ResponseBody
  public String helloForm() {
    return "<html>" +
            "<body>" +
            "<form action='hello' method='post'>" + // submit request to /hello
            "<input type='text' name='name'>" +
            "<input type='submit' value='Greet me!'/>" +
            "</form>" +
            "</body>" +
            "</html>";
  }
}
