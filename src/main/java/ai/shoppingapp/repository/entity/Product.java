package ai.shoppingapp.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Product {


    private String id;
    private String categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;

    private int isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String createdUserId;
    private String updatedUserId;

    private int isDiscount;
    private int discountProduct;
    private LocalDate discountDuration;

    public Product() {
    }

    public Product(String id, String categoryId, String name,
                    String description, BigDecimal price, String image,
                    int isActive, LocalDateTime createdAt,
                    LocalDateTime updatedAt, String createdUserId,
                    String updatedUserId, int isDiscount,
                    int discountProduct, LocalDate  discountDuration) {

        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdUserId = createdUserId;
        this.updatedUserId = updatedUserId;
        this.isDiscount = isDiscount;
        this.discountProduct = discountProduct;
        this.discountDuration = discountDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public int getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(int isDiscount) {
        this.isDiscount = isDiscount;
    }

    public int getDiscountProduct() {
        return discountProduct;
    }

    public void setDiscountProduct(int discountProduct) {
        this.discountProduct = discountProduct;
    }

    public LocalDate  getDiscountDuration() {
        return discountDuration;
    }

    public void setDiscountDuration(LocalDate  discountDuration) {
        this.discountDuration = discountDuration;
    }
}
	
	
