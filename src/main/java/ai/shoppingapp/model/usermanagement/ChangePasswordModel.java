package ai.shoppingapp.model.usermanagement;

public class ChangePasswordModel {
	private String id;
	private String oldPassword;
	private String newPassword;
	public ChangePasswordModel() {
		
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
