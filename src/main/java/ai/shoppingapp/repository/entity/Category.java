package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class Category {
	private String id;
	private String name;
	private String description;
	private String image;
	private boolean is_active;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private String created_user_id;
	private String updated_user_id;
	
	public Category() {}
	public Category(String id,String name,String description,String image,boolean is_active,
			LocalDateTime created_at,LocalDateTime updated_at,String created_user_id,String updated_user_id) {
		this.id=id;
		this.name=name;
		this.description=description;
		this.image=image;
		this.is_active=is_active;
		this.created_at=created_at;
		this.updated_at=updated_at;
		this.created_user_id=created_user_id;
		this.updated_user_id=updated_user_id;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
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
	
	

}
