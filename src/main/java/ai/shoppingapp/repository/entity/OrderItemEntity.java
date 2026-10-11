package ai.shoppingapp.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemEntity {
	private String id;
	private String order_id;
	private String stock_id;
	private BigDecimal price;
	private int quantity;
	private BigDecimal subtotal;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	public OrderItemEntity() {}
	
	public OrderItemEntity(String id,String order_id,String stock_id,BigDecimal price,
			int quantity,BigDecimal subtotal,LocalDateTime created_at,LocalDateTime updated_at) {
		this.id=id;
		this.order_id=order_id;
		this.stock_id=stock_id;
		this.price=price;
		this.quantity=quantity;
		this.subtotal=subtotal;
		this.created_at=created_at;
		this.updated_at=updated_at;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getStock_id() {
		return stock_id;
	}

	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
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
	
	

}
