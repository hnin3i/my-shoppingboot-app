package ai.shoppingapp.model;

import java.util.List;

public class PlaceOrderRequestDto {
	private String userId;
	private String fullName;
	private String shippingAddress;
	private String phoneNo;
	private String paymentMethod;
	private String orderNotes;
	private String paymentProof;
	private List<OrderItemRequestDto> items;
	
	public PlaceOrderRequestDto() {}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderNotes() {
		return orderNotes;
	}

	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	public List<OrderItemRequestDto> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequestDto> items) {
		this.items = items;
	}

	public String getPaymentProof() {
		return paymentProof;
	}

	public void setPaymentProof(String paymentProof) {
		this.paymentProof = paymentProof;
	}
	
	

}
