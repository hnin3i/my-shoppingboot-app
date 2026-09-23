package ai.shoppingapp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.service.CategoryService;
import ai.shoppingapp.service.ProductService;

@Controller
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;

	public ProductController(ProductService productService, CategoryService categoryService) {

		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping("/admin/products")
	public String productList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {

		if (keyword == null || keyword.trim().isEmpty()) {

			model.addAttribute("products", productService.findAll());

		} else {

			model.addAttribute("products", productService.search(keyword.trim()));
		}

		model.addAttribute("categories", categoryService.findAll());

		model.addAttribute("keyword", keyword);

		return "admin/products/list";
	}

@GetMapping("/admin/products/add")
	public String addProduct(Model model) {

		ProductModel newProduct = new ProductModel();

		newProduct.setIsActive(1);
		newProduct.setIsDiscount(0);
		newProduct.setDiscountProduct(0);

		model.addAttribute("product", newProduct);

		model.addAttribute("categories", categoryService.findAll());

		return "admin/products/add";
	}

@PostMapping("/admin/products/add")
	public String addProduct(@ModelAttribute("product") ProductModel product,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

	if (imageFile != null && !imageFile.isEmpty()) {

			String fileName = imageFile.getOriginalFilename();

			String uploadPath = "D:/shopping/my-shoppingboot-app/src/main/resources/static/images/product/";

			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			File file = new File(uploadPath + fileName);

			imageFile.transferTo(file);

			product.setImage(fileName);
		}

		product.setCreatedUserId("1");
		product.setUpdatedUserId("1");

		productService.save(product);

		return "redirect:/admin/products";
	}

@GetMapping("/admin/products/detail/{id}")
	public String productDetail(@PathVariable String id, Model model) {

		ProductModel product = productService.findById(id);

		model.addAttribute("product", product);

		return "admin/products/detail";
	}

@GetMapping("/admin/products/edit/{id}")
	public String editProduct(@PathVariable String id, Model model) {

		ProductModel product = productService.findById(id);
		
		System.out.println(" duration in edit get .... " + product.getDiscountDuration());

		model.addAttribute("product", product);

		model.addAttribute("categories", categoryService.findAll());

		return "admin/products/edit";
	}

@PostMapping("/admin/products/edit")
	public String editProduct(@ModelAttribute("product") ProductModel product,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

		if (imageFile != null && !imageFile.isEmpty()) {

			String fileName = imageFile.getOriginalFilename();

			String uploadPath = "D:/shopping/my-shoppingboot-app/src/main/resources/static/images/product/";

			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			File file = new File(uploadPath + fileName);

			imageFile.transferTo(file);

			product.setImage(fileName);
		}

		product.setUpdatedUserId("1");

		System.out.println(" duration .. " + product.getDiscountDuration());
		productService.update(product);

		return "redirect:/admin/products";
	}

@GetMapping("/admin/products/delete/{id}")
	public String deleteProduct(@PathVariable String id, Model model) {

		ProductModel product = productService.findById(id);

		model.addAttribute("product", product);

		return "admin/products/delete";
	}

@GetMapping("/admin/products/delete-confirm/{id}")
	public String deleteConfirm(@PathVariable String id) {

		productService.delete(id);

		return "redirect:/admin/products";
	}
}