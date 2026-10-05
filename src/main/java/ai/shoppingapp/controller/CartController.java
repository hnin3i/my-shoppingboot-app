package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ai.shoppingapp.model.UserModel;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	
	
	@GetMapping("/index")
    public String viewProduct() {
        return "cart/index"; 
    }

	@GetMapping("/cart")
    public String viewCart() {
        return "cart/cart"; 
    }
	
	@GetMapping("/cart/checkout")
    public String viewCheckout(HttpSession session,
    		Model model) {
		UserModel loggedInUser=(UserModel) session.getAttribute("loggedInUser");
		if(loggedInUser==null) {
			return "redirect:/login";
		}
		model.addAttribute("user", loggedInUser);
        return "cart/checkout"; 
    }
	
	@GetMapping("/cart/order-success")
    public String viewOrderSuccess() {
        return "cart/order-success"; 
    }
}
