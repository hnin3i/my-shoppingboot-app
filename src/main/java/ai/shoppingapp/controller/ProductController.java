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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.shoppingapp.exception.ResourceNotFoundException;
import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.model.Role;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.repository.StockRepository;
import ai.shoppingapp.service.CategoryService;
import ai.shoppingapp.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;
	private final StockRepository stockRepository;

	public ProductController(ProductService productService, CategoryService categoryService,StockRepository stockRepository) {

		this.productService = productService;
		this.categoryService = categoryService;
		this.stockRepository = stockRepository;
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
		 model.addAttribute("activePage", "products");

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
		model.addAttribute("activePage", "products");

		return "admin/products/add";
	}

@PostMapping("/admin/products/add")
	public String addProduct(@ModelAttribute("product") ProductModel product,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,HttpSession session) throws IOException {
	UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
	if (imageFile != null && !imageFile.isEmpty()) {

			String fileName = imageFile.getOriginalFilename();

			String uploadPath = "C:\\JWD-69\\FinalProject\\shopping_app\\my-shoppingboot-app\\src\\main\\resources\\static\\images\\product\\";

			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			File file = new File(uploadPath + fileName);

			imageFile.transferTo(file);

			product.setImage(fileName);
		}

	String currentUserId = String.valueOf(currentUser.getId());
	product.setCreatedUserId(currentUserId);
	product.setUpdatedUserId(currentUserId);

		productService.save(product);

		return "redirect:/admin/products";
	}

@GetMapping("/admin/products/detail/{id}")
	public String productDetail(@PathVariable String id, Model model) {

		ProductModel product = productService.findById(id);

		model.addAttribute("product", product);
		model.addAttribute("activePage", "products");
		
		return "admin/products/detail";
	}

@GetMapping("/admin/products/edit/{id}")
	public String editProduct(@PathVariable String id, Model model,HttpSession session) {

	UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
	if (currentUser == null) {
        return "redirect:/login";
    }
	
		ProductModel product = productService.findById(id);
		
		String loggedInUserId = String.valueOf(currentUser.getId());
	    String createdUserId = product.getCreatedUserId() != null ? String.valueOf(product.getCreatedUserId()) : "";

	    boolean isSuperAdmin = Role.SUPER_ADMIN.equals(currentUser.getRole());
	    boolean isOwner = loggedInUserId.equals(createdUserId);

	    if (!isSuperAdmin && !isOwner) {
	        throw new ResourceNotFoundException("Page not found / Access Denied");
	    }
		
		System.out.println(" duration in edit get .... " + product.getDiscountDuration());

		model.addAttribute("product", product);

		model.addAttribute("categories", categoryService.findAll());
		
		model.addAttribute("activePage", "products");

		return "admin/products/edit";
	}

@PostMapping("/admin/products/edit")
	public String editProduct(@ModelAttribute("product") ProductModel product,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,HttpSession session) throws IOException {

	UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
	if (currentUser == null) {
        return "redirect:/login";
    }
	
	ProductModel existingProduct = productService.findById(product.getId());
    if (existingProduct == null) {
        throw new ResourceNotFoundException("Product not found");
    }

    String loggedInUserId = String.valueOf(currentUser.getId());
    String createdUserId = existingProduct.getCreatedUserId() != null ? String.valueOf(existingProduct.getCreatedUserId()) : "";

    boolean isSuperAdmin = Role.SUPER_ADMIN.equals(currentUser.getRole());
    boolean isOwner = loggedInUserId.equals(createdUserId);

    if (!isSuperAdmin && !isOwner) {
        throw new ResourceNotFoundException("Page not found / Access Denied");
    }
	
		if (imageFile != null && !imageFile.isEmpty()) {

			String fileName = imageFile.getOriginalFilename();


			String uploadPath = "C:\\JWD-69\\FinalProject\\shopping_app\\my-shoppingboot-app\\src\\main\\resources\\static\\images\\product\\";



			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			File file = new File(uploadPath + fileName);

			imageFile.transferTo(file);

			product.setImage(fileName);
		}

		//String currentUserId = String.valueOf(currentUser.getId());
		//product.setUpdatedUserId(currentUserId);
		
		product.setUpdatedUserId(loggedInUserId);

		System.out.println(" duration .. " + product.getDiscountDuration());
		productService.update(product);

		return "redirect:/admin/products";
	}

@GetMapping("/admin/products/delete/{id}")
	public String deleteProduct(@PathVariable String id,HttpSession session, Model model) {

	UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
    if (currentUser == null || !Role.SUPER_ADMIN.equals(currentUser.getRole())) {
        throw new ResourceNotFoundException("Page not found");
    }
	
		ProductModel product = productService.findById(id);

		model.addAttribute("product", product);
		model.addAttribute("activePage", "products");

		return "admin/products/delete";
	}

@GetMapping("/admin/products/delete-confirm/{id}")
	public String deleteConfirm(@PathVariable String id,HttpSession session, Model model,RedirectAttributes redirectAttributes) {

	UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
    if (currentUser == null || !Role.SUPER_ADMIN.equals(currentUser.getRole())) {
        throw new ResourceNotFoundException("Page not found");
    }
    
 // Stock ရှိမရှိ စစ်ဆေး
    boolean hasStock = stockRepository.existsByProductId(id);
    if (hasStock) {
        redirectAttributes.addFlashAttribute("errorMessage", "You can't delete this product because you have stock!");
        return "redirect:/admin/products/delete/" + id;
    }
	
		productService.delete(id);
		model.addAttribute("activePage", "products");
		
		return "redirect:/admin/products";
	}
}