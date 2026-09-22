package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ai.shoppingapp.model.StockModel;
import ai.shoppingapp.service.StockService;

@Controller
public class StockController {

	private final StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}

	// LIST
	@GetMapping("/admin/stock")
	public String stockList(Model model) {

		model.addAttribute("stocks", stockService.findAll());
		model.addAttribute("totalQuantity", stockService.getTotalQuantity());
		model.addAttribute("productCount", stockService.getProductCount());
		model.addAttribute("categoryCount", stockService.getCategoryCount());
		model.addAttribute("lowStockCount", stockService.getLowStockCount());
		model.addAttribute("activePage", "stock");

		return "admin/stocks/list";
	}

	// CREATE FORM
	@GetMapping("/admin/stock/add")
	public String addStock(Model model) {

		model.addAttribute("stock", new StockModel());
		model.addAttribute("products", stockService.getProductOptions());
		model.addAttribute("activePage", "stock");

		return "admin/stocks/add";
	}

	// CREATE
	@PostMapping("/admin/stock/add")
	public String addStock(@ModelAttribute("stock") StockModel stock, Model model) {

		if (stock.getProductsId() == null || stock.getProductsId().isBlank()) {

			model.addAttribute("error", "Please select a product.");
			model.addAttribute("products", stockService.getProductOptions());

			return "admin/stocks/add";
		}

		stockService.add(stock);

		return "redirect:/admin/stock";
	}

	// EDIT FORM
	@GetMapping("/admin/stock/edit/{id}")
	public String editStock(@PathVariable String id, Model model) {

		StockModel stock = stockService.findById(id);

		model.addAttribute("stock", stock);
		model.addAttribute("products", stockService.getProductOptions());
		model.addAttribute("activePage", "stock");

		return "admin/stocks/edit";
	}

	// UPDATE
	@PostMapping("/admin/stock/edit")
	public String editStock(@ModelAttribute("stock") StockModel stock, Model model) {

		if (stock.getProductsId() == null || stock.getProductsId().isBlank()) {

			model.addAttribute("error", "Please select a product.");
			model.addAttribute("products", stockService.getProductOptions());

			return "admin/stocks/edit";
		}

		stockService.edit(stock.getId(), stock);

		return "redirect:/admin/stock";
	}

	// DELETE CONFIRM PAGE
	@GetMapping("/admin/stock/delete/{id}")
	public String deleteStock(@PathVariable String id, Model model) {

		StockModel stock = stockService.findById(id);

		model.addAttribute("stock", stock);
		model.addAttribute("activePage", "stock");

		return "admin/stocks/delete";
	}

	// DELETE CONFIRM - HARD DELETE
	@PostMapping("/admin/stock/delete")
	public String deleteConfirm(@ModelAttribute("stock") StockModel stock) {

		stockService.delete(stock.getId());

		return "redirect:/admin/stock";
	}
}
