package ai.shoppingapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ai.shoppingapp.model.CategoryModel;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.service.CategoryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    private final CategoryService categoryService;

    public CartController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ==========================================
    // VIEW CART
    // ==========================================

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {

        UserModel loggedInUser =
                (UserModel) session.getAttribute("loggedInUser");

        if (loggedInUser != null &&
                "ADMIN".equalsIgnoreCase(
                        String.valueOf(loggedInUser.getRole()))) {
            return "redirect:/";
        }

        List<CategoryModel> categories =
                categoryService.getAllActiveCategories();

        model.addAttribute("categories", categories);

        return "cart/cart";
    }

    // ==========================================
    // CHECKOUT
    // ==========================================

    @GetMapping("/cart/checkout")
    public String viewCheckout(
            HttpSession session,
            Model model) {

        UserModel loggedInUser =
                (UserModel) session.getAttribute("loggedInUser");

        // Not logged in
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Admin cannot checkout
        if ("ADMIN".equalsIgnoreCase(
                String.valueOf(loggedInUser.getRole()))) {

            return "redirect:/";
        }

        List<CategoryModel> categories =
                categoryService.getAllActiveCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("user", loggedInUser);

        return "cart/checkout";
    }

    // ==========================================
    // ORDER SUCCESS
    // ==========================================

    @GetMapping("/cart/order-success")
    public String viewOrderSuccess(
            HttpSession session) {

        UserModel loggedInUser =
                (UserModel) session.getAttribute("loggedInUser");

        // Not logged in
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Admin cannot access order success page
        if ("ADMIN".equalsIgnoreCase(
                String.valueOf(loggedInUser.getRole()))) {

            return "redirect:/";
        }

        return "cart/order-success";
    }
}