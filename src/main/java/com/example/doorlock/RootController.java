package com.example.doorlock;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }
}
