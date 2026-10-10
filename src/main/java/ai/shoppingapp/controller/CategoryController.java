package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.shoppingapp.exception.ResourceNotFoundException;
import ai.shoppingapp.model.CategoryModel;
import ai.shoppingapp.model.Role;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.service.CategoryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// LIST
	@GetMapping("/admin/categories")
	public String categoryList(Model model) {

		model.addAttribute("categories", categoryService.findAll());
		 model.addAttribute("activePage", "categories");

		return "admin/categories/list";
	}

	// CREATE FORM
	@GetMapping("/admin/categories/add")
	public String addCategory(Model model) {

		CategoryModel newCategory = new CategoryModel();

		newCategory.setIsActive(1);

		model.addAttribute("category", newCategory);
		 model.addAttribute("activePage", "categories");

		return "admin/categories/add";
	}

	// CREATE
	@PostMapping("/admin/categories/add")
	public String addCategory(@ModelAttribute("category") CategoryModel category,HttpSession session, Model model) {

		UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
		if (currentUser == null || !Role.ADMIN.equals(currentUser.getRole())) {
			throw new ResourceNotFoundException("Page not found");
		}
		
		boolean exists = categoryService.existsByName(category.getName());

		if (exists) {

			model.addAttribute("error", "Category name already exists!");

			return "admin/categories/add";
		}

		String currentUserId = String.valueOf(currentUser.getId());
		category.setCreatedUserId(currentUserId);
		category.setUpdatedUserId(currentUserId);

		categoryService.add(category);

		return "redirect:/admin/categories";
	}

	// DETAIL
	@GetMapping("/admin/categories/detail/{id}")
	public String categoryDetail(@PathVariable String id, Model model) {

		CategoryModel category = categoryService.findById(id);

		model.addAttribute("category", category);
		 model.addAttribute("activePage", "categories");

		return "admin/categories/detail";
	}

	// EDIT FORM
	@GetMapping("/admin/categories/edit/{id}")
	public String editCategory(@PathVariable String id,HttpSession session, Model model) {

		UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
	    if (currentUser == null) {
	        return "redirect:/login";
	    }
		
		CategoryModel category = categoryService.findById(id);
		
		String loggedInUserId = String.valueOf(currentUser.getId());
	    String createdUserId = category.getCreatedUserId() != null ? String.valueOf(category.getCreatedUserId()) : "";

		boolean isSuperAdmin = Role.SUPER_ADMIN.equals(currentUser.getRole());
		boolean isOwner = loggedInUserId.equals(createdUserId);

	    if (!isSuperAdmin && !isOwner) {
	        throw new ResourceNotFoundException("Page not found / Access Denied");
	    }

		model.addAttribute("category", category);
		 model.addAttribute("activePage", "categories");

		return "admin/categories/edit";
	}

	// UPDATE
	@PostMapping("/admin/categories/edit")
	public String editCategory(@ModelAttribute("category") CategoryModel category,HttpSession session, Model model) {

		UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
		if (currentUser == null) {
	        return "redirect:/login";
	    }
		
		CategoryModel existingCategory = categoryService.findById(category.getId());
	    if (existingCategory == null) {
	        throw new ResourceNotFoundException("Category not found");
	    }

	    String loggedInUserId = String.valueOf(currentUser.getId());
	    String createdUserId = existingCategory.getCreatedUserId() != null ? String.valueOf(existingCategory.getCreatedUserId()) : "";
	    
	    boolean isSuperAdmin = Role.SUPER_ADMIN.equals(currentUser.getRole());
	    boolean isOwner = loggedInUserId.equals(createdUserId);

	    if (!isSuperAdmin && !isOwner) {
	        throw new ResourceNotFoundException("Page not found / Access Denied");
	    }
		
		boolean exists = categoryService.existsByName(category.getName(), category.getId());

		if (exists) {

			model.addAttribute("error", "Category name already exists!");

			return "admin/categories/edit";
		}

		String currentUserId = String.valueOf(currentUser.getId());
		category.setUpdatedUserId(currentUserId);

		categoryService.edit(category.getId(), category);

		return "redirect:/admin/categories";
	}

	// DELETE FORM
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable String id, Model model) {

		CategoryModel category = categoryService.findById(id);

		model.addAttribute("category", category);
		 model.addAttribute("activePage", "categories");

		return "admin/categories/delete";
	}

	// DELETE CONFIRM - HARD DELETE
	@PostMapping("/admin/categories/delete/{id}")
	public String deleteConfirm(@ModelAttribute("category") CategoryModel category,HttpSession session,RedirectAttributes redirectAttributes) {

		UserModel currentUser = (UserModel) session.getAttribute("loggedInUser");
	    if (currentUser == null || !Role.SUPER_ADMIN.equals(currentUser.getRole())) {
	        throw new ResourceNotFoundException("Page not found");
	    }
		try {
			categoryService.delete(category.getId());
		}catch(RuntimeException e) {
			// Product ရှိနေ၍ ဖျက်မရပါက Error message ကို FlashAttribute ဖြင့် ပို့မည်
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/admin/categories/delete/" + category.getId();
		}
		
		
		return "redirect:/admin/categories";
		
	}
}