package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ai.shoppingapp.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /*@GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard/dashboard";
    }*/
    @GetMapping("/profile")
    public String adminProfile() {
        return "admin/profile/profile";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("activePage", "users");
        return "admin/users/list";
    }

  
}