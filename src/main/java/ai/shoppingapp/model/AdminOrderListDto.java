package ai.shoppingapp.model;

import java.sql.Timestamp;

public class AdminOrderListDto {
	
	private String id;
    private String orderNumber;
    private String customerName;
    private String customerPhone;
    private Timestamp createdAt;
    private double totalAmount;
    private PaymentStatus paymentStatus;
    private OrderStatus status;
    private String paymentConfirmPhoto;

    public AdminOrderListDto() {}
    public AdminOrderListDto(String id,String orderNumber,String customerName,String customerPhone,Timestamp createdAt,
    		double totalAmount,PaymentStatus paymentStatus,OrderStatus status,String paymentConfirmPhoto) {
    	this.id=id;
    	this.orderNumber=orderNumber;
    	this.customerName=customerName;
    	this.customerPhone=customerPhone;
    	this.createdAt=createdAt;
    	this.totalAmount=totalAmount;
    	this.paymentStatus=paymentStatus;
    	this.status=status;
    	this.paymentConfirmPhoto=paymentConfirmPhoto;
    	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public String getPaymentConfirmPhoto() {
		return paymentConfirmPhoto;
	}
	public void setPaymentConfirmPhoto(String paymentConfirmPhoto) {
		this.paymentConfirmPhoto = paymentConfirmPhoto;
	}
    

}
