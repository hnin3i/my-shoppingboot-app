package ai.shoppingapp.model;

public class StockModel {

	private String id;
	private String productId;
	private String colour;
	private String size;
	private int stockQty;
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
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
