package ai.shoppingapp.model;

import java.math.BigDecimal;

public class OrderDetailsDto {
	
	private String productName;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
    private String image;
    
    public OrderDetailsDto() {}
    public OrderDetailsDto(String productName,String color,String size,BigDecimal price,Integer quantity,BigDecimal subtotal,String image) {
    	this.productName=productName;
    	this.color=color;
    	this.size=size;
    	this.price=price;
    	this.quantity=quantity;
    	this.subtotal=subtotal;
    	this.image=image;
    }
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
    

}
