package ai.shoppingapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ai.shoppingapp.model.CategoryModel;
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
	public String productList(
	        @RequestParam(value = "categoryId", required = false) String categoryID,
	        @RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "newProducts", required = false) Boolean showNewProducts,
	        @RequestParam(value = "sale", required = false) Boolean showSale,
	        Model model) {

	    List<ProductModel> products;

	    List<CategoryModel> categories =
	            categoryService.getAllActiveCategories();

	    List<ProductModel> newProducts =
	            productService.findNewProducts();

	    // NEW PRODUCTS
	    if (Boolean.TRUE.equals(showNewProducts)) {

	        products = productService.findNewProducts();

	    }

	    // SALE PRODUCTS
	    else if (Boolean.TRUE.equals(showSale)) {

	        products = productService.findSaleProducts();

	    }

	    // SEARCH
	    else if (keyword != null && !keyword.trim().isEmpty()) {

	        products = productService.searchproduct(keyword.trim());

	        if (products.isEmpty()) {
	            products = productService.findAll();
	        }

	    }

	    // CATEGORY
	    else if (categoryID != null && !categoryID.isEmpty()) {

	        products = productService.findByCategoryId1(categoryID);

	        CategoryModel selectedCategory =
	                categoryService.findById(categoryID);

	        model.addAttribute("selectedCategory", selectedCategory);

	    }

	    // ALL PRODUCTS
	    else {

	        products = productService.findAll();

	    }

	    model.addAttribute("products", products);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("categories", categories);
	    model.addAttribute("newProducts", newProducts);

	    return "products/list";
	}

}
