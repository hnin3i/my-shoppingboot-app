package ai.shoppingapp.model;

public class AdminPaymentUpdateDto {
	
	private String orderId;
    private OrderStatus status;
    private PaymentStatus paymentStatus;

    public AdminPaymentUpdateDto() {}
    public AdminPaymentUpdateDto(String orderId,OrderStatus status,PaymentStatus paymentStatus) {
    	this.orderId=orderId;
    	this.status=status;
    	this.paymentStatus=paymentStatus;
    }
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
    

}
