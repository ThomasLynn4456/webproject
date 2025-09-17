package com.example.doorlock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private static final String[][] USERS = {
            {"linlin4456@gmail.com", "linlin123"},
            {"hsulae123@gmail.com", "hsulae123"},
            {"kyalsinmon123@gmail.com", "kyalsin123"},
            {"shweyatimin123@gmail.com", "shweshwe123"}
    };

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "login"; // login.html in templates
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        for (String[] user : USERS) {
            if (user[0].equals(email) && user[1].equals(password)) {
                return "redirect:/index";
            }
        }
        redirectAttributes.addAttribute("error", "Invalid email or password!");
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String showIndexPage() {
        return "index"; // index.html in templates
    }
}
