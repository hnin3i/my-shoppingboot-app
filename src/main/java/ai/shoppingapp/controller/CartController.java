package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String viewCheckout() {
        return "cart/checkout"; 
    }
	
	@GetMapping("/cart/order-success")
    public String viewOrderSuccess() {
        return "cart/order-success"; 
    }
}
