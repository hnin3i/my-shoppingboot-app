package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ai.shoppingapp.model.AdminDashboardDto;
import ai.shoppingapp.service.AdminDashboardService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
	
	private final AdminDashboardService service;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.service = dashboardService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        AdminDashboardDto dashboardData = service.getDashboardSummary();
        model.addAttribute("dashboard", dashboardData);
        return "admin/dashboard/dashboard"; // templates/admin/dashboard.html
    }

}
