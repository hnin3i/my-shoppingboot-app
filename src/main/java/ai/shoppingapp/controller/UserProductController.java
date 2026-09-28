package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.service.ProductService;

@Controller
public class UserProductController {
	
	private final ProductService productService;

	public UserProductController(ProductService productService) {

		this.productService = productService;
		
	}

	
	@GetMapping("products/detail/{id}")
	public String productDetail(@PathVariable String id, Model model) {
		
		ProductModel product = productService.findById(id);

		model.addAttribute("product", product);

		return "products/detail";
		
	}
	 

}
