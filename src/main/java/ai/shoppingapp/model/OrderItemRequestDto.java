package ai.shoppingapp.model;

public class OrderItemRequestDto {
	private String stockId;
	private int quantity;
	
	public OrderItemRequestDto() {}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}
