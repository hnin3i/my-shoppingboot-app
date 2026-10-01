package ai.shoppingapp.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class AdminOrderDetailDto {
	
	private String id;
    private String orderNumber;
    private Timestamp createdAt;
    
    // Customer Info
    private String customerName;
    private String customerPhone;
    private String shippingAddress;

    // Amounts
    private BigDecimal subtotalAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;

    // Payment & Status
    private String paymentMethod;
    private String paymentConfirmPhoto;
    private PaymentStatus paymentStatus;
    private OrderStatus status;

    // Itemized Order List
    private List<AdminOrderItemDetailDto> items;

    public AdminOrderDetailDto() {}
    public AdminOrderDetailDto(String id,String orderNumber,Timestamp createdAt,String customerName,String customerPhone,String shippingAddress,BigDecimal subtotalAmount,
    		BigDecimal taxAmount,BigDecimal shippingFee,BigDecimal totalAmount,String paymentMethod,String paymentConfirmPhoto,PaymentStatus paymentStatus,OrderStatus status,List<AdminOrderItemDetailDto> items) {
    	this.id=id;
    	this.orderNumber=orderNumber;
    	this.createdAt=createdAt;
    	this.customerName=customerName;
    	this.customerPhone=customerPhone;
    	this.shippingAddress=shippingAddress;
    	this.subtotalAmount=subtotalAmount;
    	this.taxAmount=taxAmount;
    	this.shippingFee=shippingFee;
    	this.totalAmount=totalAmount;
    	this.paymentMethod=paymentMethod;
    	this.paymentConfirmPhoto=paymentConfirmPhoto;
    	this.paymentStatus=paymentStatus;
    	this.status=status;
    	this.items=items;
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
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
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
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public BigDecimal getSubtotalAmount() {
		return subtotalAmount;
	}
	public void setSubtotalAmount(BigDecimal subtotalAmount) {
		this.subtotalAmount = subtotalAmount;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	public BigDecimal getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentConfirmPhoto() {
		return paymentConfirmPhoto;
	}
	public void setPaymentConfirmPhoto(String paymentConfirmPhoto) {
		this.paymentConfirmPhoto = paymentConfirmPhoto;
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
	public List<AdminOrderItemDetailDto> getItems() {
		return items;
	}
	public void setItems(List<AdminOrderItemDetailDto> items) {
		this.items = items;
	}
    
	
	

}
