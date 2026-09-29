package ai.shoppingapp.repository.entity;

import java.sql.Timestamp;

public class OrderDetailsEntity {
	
	private String id;
	private String order_id;
	private String stock_id;
	private double price;
	private Integer quantity;
	private double subtotal;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	private String productName;
	private String image;
	private String colour;
	private String size;
	
	public OrderDetailsEntity() {}
	public OrderDetailsEntity(String id,String order_id,String stock_id,double price,Integer quantity,
			double subtotal,Timestamp created_at,Timestamp updated_at,String productName,String image,String colour,String size) {
		this.id=id;
		this.order_id=order_id;
		this.stock_id=stock_id;
		this.price=price;
		this.quantity=quantity;
		this.subtotal=subtotal;
		this.created_at=created_at;
		this.updated_at=updated_at;
		this.productName=productName;
		this.image=image;
		this.colour=colour;
		this.size=size;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	

}
