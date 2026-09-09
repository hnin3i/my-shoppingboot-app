package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class Order {
	private String id;
	private String order_number;
	private double total_amount;
	private String status;
	private String shipping_address;
	private String payment_method;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private String users_id;
	private String phone_no;
	
	public Order() {}
	public Order(String id,String order_number,double total_amount,String status,String shipping_address,
			String payment_method,LocalDateTime created_at,LocalDateTime updated_at,String users_id,String phone_no) {
		this.id=id;
		this.order_number=order_number;
		this.total_amount=total_amount;
		this.status=status;
		this.shipping_address=shipping_address;
		this.payment_method=payment_method;
		this.created_at=created_at;
		this.updated_at=updated_at;
		this.users_id=users_id;
		this.phone_no=phone_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
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
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	
	

}
