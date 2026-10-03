package ai.shoppingapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.model.StockModel;
import ai.shoppingapp.repository.entity.Stock;
import ai.shoppingapp.service.ProductService;
import ai.shoppingapp.service.StockService;
import ai.shoppingapp.service.CategoryService;

@Controller
public class UserProductController {
	
	private final ProductService productService;
	private final CategoryService categoryService;
	private final StockService stockService;

	public UserProductController(ProductService productService,
			CategoryService categoryService,
			StockService stockService) {

		this.productService = productService;
		this.categoryService = categoryService;
		this.stockService=stockService;
	}

	
	@GetMapping("products/detail/{id}")
	public String productDetail(@PathVariable String id, Model model) {
		
		ProductModel product = productService.findById(id);
		if(product==null) {
			return "redirect:/products/list";
			
		}
		model.addAttribute("product", product);
		
		List<StockModel> stocks=stockService.findByProductId(id);
		model.addAttribute("stocks", stocks);

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
