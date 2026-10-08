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
		this.categoryService=categoryService;
	}
	

	@GetMapping("/cart")
    public String viewCart(Model model) {
		
		List<CategoryModel> categories = categoryService.getAllActiveCategories();
	      model.addAttribute("categories", categories);
		
        return "cart/cart"; 
    }
	
	@GetMapping("/cart/checkout")
    public String viewCheckout(HttpSession session,
    		Model model) {
		UserModel loggedInUser=(UserModel) session.getAttribute("loggedInUser");
		if(loggedInUser==null) {
			return "redirect:/login";
		}
		List<CategoryModel> categories = categoryService.getAllActiveCategories();
	      model.addAttribute("categories", categories);
		model.addAttribute("user", loggedInUser);
        return "cart/checkout"; 
    }
	
	@GetMapping("/cart/order-success")
    public String viewOrderSuccess() {
        return "cart/order-success"; 
    }
}
