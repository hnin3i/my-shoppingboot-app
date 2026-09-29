package ai.shoppingapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.shoppingapp.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

	private final ProductService productService;
	
	public ProductApiController(ProductService productService) {
		this.productService=productService;
		
	}
}
