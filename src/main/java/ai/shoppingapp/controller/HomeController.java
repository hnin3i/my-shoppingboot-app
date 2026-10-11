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

    public HomeController(ProductService productService,
                          CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(value = "categoryId", required = false) String categoryId,
            Model model) {

        List<CategoryModel> categories =
                categoryService.getAllActiveCategories();

        List<ProductModel> products;

        // ================= CATEGORY =================
        if (categoryId != null && !categoryId.isBlank()) {

            products = productService.findByCategoryId1(categoryId);

            CategoryModel selectedCategory =
                    categoryService.findById(categoryId);

            model.addAttribute("selectedCategory", selectedCategory);

        } else {

            products = productService.findAll();
        }


        // ================= NEW PRODUCTS =================
        List<ProductModel> newProducts =
                productService.findNewProducts();


        // ================= SALE PRODUCTS =================
        List<ProductModel> saleProducts =
                productService.findDiscountProducts();


        model.addAttribute("categories", categories);

        // Existing products
        model.addAttribute("Products", products);

        // New In
        model.addAttribute("newProducts", newProducts);

        // Sale
        model.addAttribute("saleProducts", saleProducts);


        return "home";


    }
    @GetMapping("/error")
	public String handleDirectErrorAccess(Model model) {
	    model.addAttribute("status", 404);
	    model.addAttribute("title", "Page Not Found");
	    model.addAttribute("message", "The requested resource could not be found.");
	    return "error";
	}
}