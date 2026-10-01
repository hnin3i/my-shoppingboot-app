package ai.shoppingapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ai.shoppingapp.model.CategoryModel;
import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.service.CategoryService;
import ai.shoppingapp.service.ProductService;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
    //restore
    @GetMapping("/")
    public String home(@RequestParam(value = "categoryId", required = false) String categoryId, 
                       Model model) {
        // 1. Fetch all categories for the clickable buttons
        List<CategoryModel> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        // 2. Fetch products (either filtered by category or all products)
        List<ProductModel> products;
        if (categoryId != null && !categoryId.trim().isEmpty() && !"all".equalsIgnoreCase(categoryId)) {
            products = productService.getProductsByCategoryId(categoryId);
            model.addAttribute("selectedCategoryId", categoryId);
        } else {
            products = productService.findAll();
            model.addAttribute("selectedCategoryId", "all");
        }
        model.addAttribute("products", products);
        model.addAttribute("newProducts", products);
        return "home"; // templates/home.html
    }
    
	@GetMapping("/error")
	public String handleDirectErrorAccess(Model model) {
	    model.addAttribute("status", 404);
	    model.addAttribute("title", "Page Not Found");
	    model.addAttribute("message", "The requested resource could not be found.");
	    return "error";
	}
	
}