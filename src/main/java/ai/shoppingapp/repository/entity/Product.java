package ai.shoppingapp.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Product {

    private String id;
    private String category_id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;

 
    private boolean is_active;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private String created_user_id;
    private String updated_user_id;

    private boolean is_discount;
    private int discount_product;
    private LocalDate discount_duration;
    private BigDecimal discount_price;
    private BigDecimal final_price;

    public Product() {
    }

    public Product(String id, String category_id, String name,
                    String description, BigDecimal price, String image,
                    boolean is_active, LocalDateTime created_at,
                    LocalDateTime updated_at, String created_user_id,
                    String updated_user_id, boolean is_discount,
                    int discount_product, LocalDate  discount_duration,
                    BigDecimal discount_price,BigDecimal final_price) {

        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.is_active =is_active;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_user_id = created_user_id;
        this.updated_user_id = updated_user_id;
        this.is_discount = is_discount;
        this.discount_product = discount_product;
        this.discount_duration = discount_duration;
        this.discount_price=discount_price;
        this.final_price=final_price;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public LocalDate getDiscount_duration() {
		return discount_duration;
	}

	public void setDiscount_duration(LocalDate discount_duration) {
		this.discount_duration = discount_duration;
	}

	public BigDecimal getDiscount_price() {
		return discount_price;
	}

	public void setDiscount_price(BigDecimal discount_price) {
		this.discount_price = discount_price;
	}

	public BigDecimal getFinal_price() {
		return final_price;
	}

	public void setFinal_price(BigDecimal final_price) {
		this.final_price = final_price;
	}
    
	
}
