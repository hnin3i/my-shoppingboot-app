package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.service.ProductService;
import ai.shoppingapp.service.CategoryService;

@Controller
public class UserProductController {
	
	private final ProductService productService;
	private final CategoryService categoryService;

	public UserProductController(ProductService productService, CategoryService categoryService) {

		this.productService = productService;
		this.categoryService = categoryService;
	}

	
	@GetMapping("products/detail/{id}")
	public String productDetail(@PathVariable String id, Model model) {
		
		ProductModel product = productService.findById(id);

		model.addAttribute("product", product);

		return "products/detail";
		
	}
	
	@GetMapping("products/list")
	public String productList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {

		if (keyword == null || keyword.trim().isEmpty()) {

			model.addAttribute("products", productService.findAll());

		} else {

			model.addAttribute("products", productService.search(keyword.trim()));
		}
	

		model.addAttribute("categories", categoryService.findAll());

		model.addAttribute("keyword", keyword);

		return "products/list";
	}
	 

}
