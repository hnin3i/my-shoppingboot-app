package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

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
    public String users() {
        return "admin/users/login";
    }
}
