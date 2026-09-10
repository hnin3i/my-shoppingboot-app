package ai.shoppingapp.model.usermanagement;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginModel {
	@NotBlank(message = "Email is required.")
    @Email(message = "Please enter a valid email address")
	private String email;
	private String password;
	
	public LoginModel(){}

	public LoginModel(@NotBlank(message = "Email is required.") String email, String password) {
		this.email = email;
		this.password = password;
	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
