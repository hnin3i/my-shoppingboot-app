package ai.shoppingapp.model;

import java.util.List;

public class AdminDashboardDto {
	
	private int totalProducts;
    private int totalCategories;
    private int totalUsers;
    private int totalOrders;
    private int totalStock;
    private int lowStockCount;
    private List<AdminOrderListDto> recentOrders;
    
    public AdminDashboardDto() {}
    public AdminDashboardDto(int totalProducts,int totalCategories,int totalUsers,int totalOrders,int totalStock,int lowStockCount,List<AdminOrderListDto> recentOrders) {
    	this.totalProducts=totalProducts;
    	this.totalCategories=totalCategories;
    	this.totalUsers=totalUsers;
    	this.totalOrders=totalOrders;
    	this.totalStock=totalStock;
    	this.lowStockCount=lowStockCount;
    	this.recentOrders=recentOrders;
    }
	public int getTotalProducts() {
		return totalProducts;
	}
	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}
	public int getTotalCategories() {
		return totalCategories;
	}
	public void setTotalCategories(int totalCategories) {
		this.totalCategories = totalCategories;
	}
	public int getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}
	public int getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	public int getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(int totalStock) {
		this.totalStock = totalStock;
	}
	public int getLowStockCount() {
		return lowStockCount;
	}
	public void setLowStockCount(int lowStockCount) {
		this.lowStockCount = lowStockCount;
	}
	public List<AdminOrderListDto> getRecentOrders() {
		return recentOrders;
	}
	public void setRecentOrders(List<AdminOrderListDto> recentOrders) {
		this.recentOrders = recentOrders;
	}

}
