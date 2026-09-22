package ai.shoppingapp.model;

public class StockModel {

	private String id;
	private int stockQty;
	private String productsId;
	private String productName;

	// Computed in the service layer, not stored in the database
	private boolean lowStock;

	public StockModel() {}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	public String getProductsId() {
		return productsId;
	}
	public void setProductsId(String productsId) {
		this.productsId = productsId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public boolean isLowStock() {
		return lowStock;
	}
	public void setLowStock(boolean lowStock) {
		this.lowStock = lowStock;
	}
}
