package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.shoppingapp.model.Role;
import ai.shoppingapp.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/stock")
    public String stock() {
        return "admin/stocks/list";
    }

    @GetMapping("/products")
    public String products() {
        return "admin/products/list";
    }

    @GetMapping("/categories")
    public String categories() {
        return "admin/categories/list";
    }

    @GetMapping("/orders")
    public String orders() {
        return "admin/orders/list";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("activePage", "users");
        return "admin/users/list";
    }

  
}