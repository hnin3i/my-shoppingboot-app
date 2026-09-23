package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class Order1 {

	private String id;
	private String user_id;
	private String order_number;
	private double subtotal_amount;
	private double tax_amount;
	private double shipping_fee;
	private double total_amount;
	private String status;
	private String payment_method;
	private String payment_status;
	private String shipping_address;
	private String phone_no;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private String payment_confirm_photo;
	private String additional_note;
	
	public Order1() {}
	public Order1(String id,String user_id,String order_number,double subtotal_amount,double tax_amount,double shipping_fee,double total_amount,String status,String payment_method,
			String payment_status,String shipping_address,String phone_no,LocalDateTime created_at,LocalDateTime updated_at,String payment_confirm_photo,
			String additional_note) {
		this.id=id;
		this.user_id=user_id;
		this.order_number=order_number;
		this.subtotal_amount=subtotal_amount;
		this.tax_amount=tax_amount;
		this.shipping_fee=shipping_fee;
		this.total_amount=total_amount;
		this.status=status;
		this.payment_method=payment_method;
		this.payment_status=payment_status;
		this.shipping_address=shipping_address;
		this.phone_no=phone_no;
		this.created_at=created_at;
		this.updated_at=updated_at;
		this.payment_confirm_photo=payment_confirm_photo;
		this.additional_note=additional_note;
		
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public double getSubtotal_amount() {
		return subtotal_amount;
	}
	public void setSubtotal_amount(double subtotal_amount) {
		this.subtotal_amount = subtotal_amount;
	}
	public double getTax_amount() {
		return tax_amount;
	}
	public void setTax_amount(double tax_amount) {
		this.tax_amount = tax_amount;
	}
	public double getShipping_fee() {
		return shipping_fee;
	}
	public void setShipping_fee(double shipping_fee) {
		this.shipping_fee = shipping_fee;
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
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public String getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
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
	public String getPayment_confirm_photo() {
		return payment_confirm_photo;
	}
	public void setPayment_confirm_photo(String payment_confirm_photo) {
		this.payment_confirm_photo = payment_confirm_photo;
	}
	public String getAdditional_note() {
		return additional_note;
	}
	public void setAdditional_note(String additional_note) {
		this.additional_note = additional_note;
	}
}
