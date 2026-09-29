package ai.shoppingapp.model;

public class AdminOrderItemDetailDto {
	
	private String productName;
    private String productImage;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    
    public AdminOrderItemDetailDto() {}
    public AdminOrderItemDetailDto(String productName,String productImage,int quantity,double unitPrice,double subtotal) {
    	this.productName=productName;
    	this.productImage=productImage;
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
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
    
    
    
    

}
