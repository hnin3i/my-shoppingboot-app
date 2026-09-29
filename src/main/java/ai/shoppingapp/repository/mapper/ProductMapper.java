package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ai.shoppingapp.model.ProductModel;

public class ProductMapper implements RowMapper<ProductModel> {

    @Override
    public ProductModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        ProductModel product = new ProductModel();

        product.setId(rs.getString("id"));

        product.setCategoryId(rs.getString("category_id"));

        product.setCategoryName(rs.getString("category_name"));

        product.setName(rs.getString("name"));

        product.setDescription(rs.getString("description"));

        product.setPrice(rs.getBigDecimal("price"));

        product.setImage(rs.getString("image"));

        product.setIsActive(rs.getInt("is_active"));

        product.setCreatedAt(
                rs.getTimestamp("created_at") != null
                        ? rs.getTimestamp("created_at").toLocalDateTime()
                        : null
        );

        product.setUpdatedAt(
                rs.getTimestamp("updated_at") != null
                        ? rs.getTimestamp("updated_at").toLocalDateTime()
                        : null
        );

        product.setCreatedUserId(
                rs.getString("created_user_id")
        );

        product.setUpdatedUserId(
                rs.getString("updated_user_id")
        );

        product.setIsDiscount(
                rs.getInt("is_discount")
        );

        product.setDiscountProduct(
                rs.getInt("discount_product")
        );

        if (rs.getDate("discount_duration") != null) {

            product.setDiscountDuration(
                rs.getDate("discount_duration")
                    .toLocalDate()
            );
        }

        product.setDiscountPrice(
                rs.getBigDecimal("discount_price")
        );

        product.setFinalPrice(
                rs.getBigDecimal("final_price")
        );


        return product;
    }
}