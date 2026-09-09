package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class Product {
	
	private String id;
	private String name;
	private String description;
	private String categories_id;
	private double price;
	private LocalDateTime created_at;
	private LocalDateTime update_at;
	private String image;
	private String created_user_id;
	private String updated_user_id;
	private boolean is_active;
	
	public Product() {}
	public Product(String id,String name,String description,String categories_id,double price,
			LocalDateTime created_at,LocalDateTime update_at,String image,String created_user_id,
			String updated_user_id,boolean is_active) {
		this.id=id;
		this.name=name;
		this.description=description;
		this.categories_id=categories_id;
		this.price=price;
		this.created_at=created_at;
		this.update_at=update_at;
		this.image=image;
		this.created_user_id=created_user_id;
		this.updated_user_id=updated_user_id;
		this.is_active=is_active;
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
	public String getCategories_id() {
		return categories_id;
	}
	public void setCategories_id(String categories_id) {
		this.categories_id = categories_id;
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
	
	

}
