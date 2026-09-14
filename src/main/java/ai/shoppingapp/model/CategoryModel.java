package ai.shoppingapp.model;

public class CategoryModel {

	private String id;
	private String name;
	private String description;
	private String image;
	private String createdUserId; 
	private String updatedUserId;
	private int isActive;

	public CategoryModel() {
	}

	public CategoryModel(String id, String name, String description, String image,String createdUserId, String updatedUserId, int isActive) {

		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.createdUserId = createdUserId;
		this.updatedUserId = updatedUserId;
		this.isActive = isActive;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public String getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
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

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
}