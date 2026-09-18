package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class Product {
	
	private String id;
	private String category_id;
	private String name;
	private String description;
	private double price;
	private String image;
	private boolean is_active;
	private LocalDateTime created_at;
	private LocalDateTime update_at;
	
	private String created_user_id;
	private String updated_user_id;
	private boolean is_discount;
	private int discount_product;
	private LocalDateTime discount_duration;
	
	
	public Product() {}
	public Product(String id,String category_id,String name,String description,double price,String image,
			boolean is_active,LocalDateTime created_at,LocalDateTime update_at,String created_user_id,
			String updated_user_id,boolean is_discount,int discount_product,LocalDateTime discount_duration) {
		this.id=id;
		this.category_id=category_id;
		this.name=name;
		this.description=description;
		this.price=price;
		this.image=image;
		this.is_active=is_active;
		this.created_at=created_at;
		this.update_at=update_at;
		
		this.created_user_id=created_user_id;
		this.updated_user_id=updated_user_id;
		this.is_discount=is_discount;
		this.discount_product=discount_product;
		this.discount_duration=discount_duration;
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(LocalDateTime update_at) {
		this.update_at = update_at;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCreated_user_id() {
		return created_user_id;
	}
	public void setCreated_user_id(String created_user_id) {
		this.created_user_id = created_user_id;
	}
	public String getUpdated_user_id() {
		return updated_user_id;
	}
	public void setUpdated_user_id(String updated_user_id) {
		this.updated_user_id = updated_user_id;
	}
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	public boolean isIs_discount() {
		return is_discount;
	}
	public void setIs_discount(boolean is_discount) {
		this.is_discount = is_discount;
	}
	public int getDiscount_product() {
		return discount_product;
	}
	public void setDiscount_product(int discount_product) {
		this.discount_product = discount_product;
	}
	public LocalDateTime getDiscount_duration() {
		return discount_duration;
	}
	public void setDiscount_duration(LocalDateTime discount_duration) {
		this.discount_duration = discount_duration;
	}
	
	

}
