package ai.shoppingapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ai.shoppingapp.model.CategoryModel;
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

	public UserProductController(ProductService productService, CategoryService categoryService,
			StockService stockService) {

		this.productService = productService;
		this.categoryService = categoryService;
		this.stockService = stockService;
	}

	@GetMapping("products/detail/{id}")
	public String productDetail(@PathVariable String id, Model model) {

		ProductModel product = productService.findById(id);
		if (product == null) {
			return "redirect:/products/list";

		}
		model.addAttribute("product", product);

		List<StockModel> stocks = stockService.findByProductId(id);
		model.addAttribute("stocks", stocks);

		List<CategoryModel> categories = categoryService.getAllActiveCategories();
	      model.addAttribute("categories", categories);

		return "products/detail";
	}

	@GetMapping("products/list")
	public String productList(
	        @RequestParam(value = "categoryId", required = false) String categoryID,
	        @RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "newArrivals", required = false) Boolean showNewArrivals,
	        @RequestParam(value = "sale", required = false) Boolean showSale,
	        Model model) {

	    List<ProductModel> products;
	    List<CategoryModel> categories = categoryService.getAllActiveCategories();

	    // NEW ARRIVALS
	    if (Boolean.TRUE.equals(showNewArrivals)) {

	        products = productService.findNewProducts();

	        model.addAttribute("pageTitle", "NEW IN");
	        model.addAttribute("pageSubtitle", "JUST ARRIVED");
	    }

	    // SALE
	    else if (Boolean.TRUE.equals(showSale)) {

	        products = productService.findSaleProducts();

	        model.addAttribute("pageTitle", "SALE");
	        model.addAttribute("pageSubtitle", "LIMITED TIME");
	    }

	    // SEARCH
	    else if (keyword != null && !keyword.trim().isEmpty()) {

	        products = productService.searchproduct(keyword.trim());

	        model.addAttribute("pageTitle", "SEARCH RESULTS");
	        model.addAttribute("pageSubtitle", "SEARCH");
	    }

	    // CATEGORY
	    else if (categoryID != null && !categoryID.isEmpty()) {

	        products = productService.findByCategoryId1(categoryID);

	        CategoryModel selectedCategory = categoryService.findById(categoryID);

	        model.addAttribute("selectedCategory", selectedCategory);
	        model.addAttribute("pageTitle", selectedCategory.getName());
	        model.addAttribute("pageSubtitle", "CATEGORY");
	    }

	    // ALL PRODUCTS
	    else {

	        products = productService.findAll();

	        model.addAttribute("pageTitle", "ALL PRODUCTS");
	        model.addAttribute("pageSubtitle", "SHOP");
	    }

	    model.addAttribute("products", products);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("categories", categories);

	    return "products/list";
	}
}
