package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class Stock {
	private String id;
	private String product_id;
	private String colour;
	private String size;
	private int stock_qty;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;

	// Not a DB column - populated only by JOIN queries for display (product name)
	private String productName;

	public Stock() {}

	public Stock(String id, String product_id, String colour, String size, int stock_qty,
			LocalDateTime created_at, LocalDateTime updated_at) {
		this.id = id;
		this.product_id = product_id;
		this.colour = colour;
		this.size = size;
		this.stock_qty = stock_qty;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
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
	public int getStock_qty() {
		return stock_qty;
	}
	public void setStock_qty(int stock_qty) {
		this.stock_qty = stock_qty;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
