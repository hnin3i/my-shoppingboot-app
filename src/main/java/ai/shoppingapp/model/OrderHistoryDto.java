package ai.shoppingapp.model;

import java.sql.Timestamp;

public class OrderHistoryDto {
	
	private String orderId;
    private String orderNumber;
    private String customerName;
    private String productNames;
    private double totalAmount;
    private String orderStatus;
    private String paymentStatus;
    private Timestamp createdAt;
    
    public OrderHistoryDto() {}
    public OrderHistoryDto(String orderId,String orderNumber,String customerName,String productNames,double totalAmount,String orderStatus,String paymentStatus,Timestamp createdAt) {
    	this.orderId=orderId;
    	this.orderNumber=orderNumber;
    	this.customerName=customerName;
    	this.productNames=productNames;
    	this.totalAmount=totalAmount;
    	this.orderStatus=orderStatus;
    	this.paymentStatus=paymentStatus;
    	this.createdAt=createdAt;
    }
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductNames() {
		return productNames;
	}
	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
    

}
