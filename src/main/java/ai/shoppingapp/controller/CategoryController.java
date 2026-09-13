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

import ai.shoppingapp.model.CategoryDto;
import ai.shoppingapp.service.CategoryService;

@Controller
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {

		this.categoryService = categoryService;
	}

// LIST
	@GetMapping("/categories")
	public String categoryList(Model model) {

		model.addAttribute("categories", categoryService.findAll());

		return "categories/list";
	}

// CREATE FORM
	@GetMapping("/categories/add")
	public String addCategory(Model model) {

		CategoryDto newCategory = new CategoryDto();

		newCategory.setIsActive(1);

		model.addAttribute("category", newCategory);

		return "categories/add";
	}

// CREATE
	@PostMapping("/categories/add")
	public String addCategory(
	        @ModelAttribute("category") CategoryDto category,
	        @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

	    if (!imageFile.isEmpty()) {

	        String fileName = imageFile.getOriginalFilename();

	        String uploadPath =
	                "D:/shopping/shoppingapp/src/main/resources/static/images/categories/";

	        File uploadDir = new File(uploadPath);

	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs();
	        }

	        File file = new File(uploadPath + fileName);

	        imageFile.transferTo(file);

	        category.setImage(fileName);
	    }

	    this.categoryService.add(category);

	    return "redirect:/categories";
	}

// DETAIL
	@GetMapping("/categories/detail/{id}")
	public String categoryDetail(@PathVariable String id, Model model) {

		CategoryDto category = this.categoryService.findById(id);

		model.addAttribute("category", category);

		return "categories/detail";
	}

// EDIT FORM
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable String id, Model model) {

		CategoryDto category = this.categoryService.findById(id);

		model.addAttribute("category", category);

		return "categories/edit";
	}

// UPDATE
	@PostMapping("/categories/edit")
	public String editCategory(@ModelAttribute("category") CategoryDto category) {

		this.categoryService.edit(category.getId(), category);

		return "redirect:/categories";
	}

// DELETE
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable String id, Model model) {

		CategoryDto category = this.categoryService.findById(id);

		model.addAttribute("category", category);

		return "categories/delete";
	}

// DELETE CONFIRM
	@PostMapping("/categories/delete")
	public String deleteConfirm(@ModelAttribute("category") CategoryDto category) {

		this.categoryService.delete(category.getId());

		return "redirect:/categories";
	}
}