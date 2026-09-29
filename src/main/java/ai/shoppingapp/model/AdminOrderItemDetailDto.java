package ai.shoppingapp.model;

import java.math.BigDecimal;

public class AdminOrderItemDetailDto {
	
	private String productName;
    private String productImage;
    private String colour;
    private String size;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    
    public AdminOrderItemDetailDto() {}
    public AdminOrderItemDetailDto(String productName,String productImage,String colour,String size,int quantity,BigDecimal unitPrice,BigDecimal subtotal) {
    	this.productName=productName;
    	this.productImage=productImage;
    	this.colour=colour;
    	this.size=size;
    	this.quantity=quantity;
    	this.unitPrice=unitPrice;
    	this.subtotal=subtotal;
    }
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
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
