package ai.shoppingapp.model;

import java.time.LocalDateTime;

public class UserModel {
	private String id;
	private String name;
	private String email;
	private String phone;
	private Role role;
	private String address;
	private String profile;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;

	public UserModel() {}

	public UserModel(String id, String name, String email, String phone, Role role, String address,
			String profile, LocalDateTime created_at, LocalDateTime updated_at) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.address = address;
		this.profile = profile;
		this.created_at = created_at;
		this.updated_at = updated_at;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
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
}
