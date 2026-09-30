package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class User {
	private String id;
	private String name;
	private String email;
	private String phone;
	private String password;
	private String role;
	private String address;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private String profile;
	
	public User() {}
	
	public User(String id,String name,String email,String phone,String password,String role,String address,
			LocalDateTime created_at, LocalDateTime updated_at,String profile) {
		this.id=id;
		this.name=name;
		this.email=email;
		this.phone=phone;
		this.password=password;
		this.role=role;
		this.address=address;
		this.created_at=created_at;
		this.updated_at=updated_at;
		this.profile=profile;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	

}
