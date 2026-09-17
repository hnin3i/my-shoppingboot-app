package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ai.shoppingapp.model.CategoryModel;
import ai.shoppingapp.service.CategoryService;

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

        return "admin/categories/list";
    }

    // CREATE FORM
    @GetMapping("/admin/categories/add")
    public String addCategory(Model model) {

        CategoryModel newCategory = new CategoryModel();

        newCategory.setIsActive(1);

        model.addAttribute("category", newCategory);

        return "admin/categories/add";
    }

    // CREATE
    @PostMapping("/admin/categories/add")
    public String addCategory(
            @ModelAttribute("category") CategoryModel category,
            Model model) {

        boolean exists =
                categoryService.existsByName(category.getName());

        if (exists) {

            model.addAttribute(
                    "error",
                    "Category name already exists!"
            );

            return "admin/categories/add";
        }

        category.setCreatedUserId("1");
        category.setUpdatedUserId("1");

        categoryService.add(category);

        return "redirect:/admin/categories";
    }

    // DETAIL
    @GetMapping("/admin/categories/detail/{id}")
    public String categoryDetail(
            @PathVariable String id,
            Model model) {

        CategoryModel category =
                categoryService.findById(id);

        model.addAttribute("category", category);

        return "admin/categories/detail";
    }

    // EDIT FORM
    @GetMapping("/admin/categories/edit/{id}")
    public String editCategory(
            @PathVariable String id,
            Model model) {

        CategoryModel category =
                categoryService.findById(id);

        model.addAttribute("category", category);

        return "admin/categories/edit";
    }

    // UPDATE
    @PostMapping("/admin/categories/edit")
    public String editCategory(
            @ModelAttribute("category") CategoryModel category,
            Model model) {

        boolean exists =
                categoryService.existsByName(
                        category.getName(),
                        category.getId()
                );

        if (exists) {

            model.addAttribute(
                    "error",
                    "Category name already exists!"
            );

            return "admin/categories/edit";
        }

        category.setUpdatedUserId("1");

        categoryService.edit(
                category.getId(),
                category
        );

        return "redirect:/admin/categories";
    }

    // DELETE FORM
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(
            @PathVariable String id,
            Model model) {

        CategoryModel category =
                categoryService.findById(id);

        model.addAttribute("category", category);

        return "admin/categories/delete";
    }

    // DELETE CONFIRM
    @PostMapping("/admin/categories/delete")
    public String deleteConfirm(
            @ModelAttribute("category") CategoryModel category) {

        categoryService.delete(category.getId());

        return "redirect:/admin/categories";
    }
}