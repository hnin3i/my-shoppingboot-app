package ai.shoppingapp.service;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.AdminDashboardDto;
import ai.shoppingapp.repository.AdminDashboardRepository;

@Service
public class AdminDashboardService {
	
	private final AdminDashboardRepository repo;

    public AdminDashboardService(AdminDashboardRepository repo) {
        this.repo = repo;
    }

    public AdminDashboardDto getDashboardSummary() {
        AdminDashboardDto dto = new AdminDashboardDto();
        
        dto.setTotalProducts(repo.getTotalProducts());
        dto.setTotalCategories(repo.getTotalCategories());
        dto.setTotalUsers(repo.getTotalUsers());
        dto.setTotalOrders(repo.getTotalOrders());
        dto.setTotalStock(repo.getTotalStock());
        dto.setLowStockCount(repo.getLowStockCount(5)); 
        dto.setRecentOrders(repo.findRecentOrders(5));

        return dto;
    }

}