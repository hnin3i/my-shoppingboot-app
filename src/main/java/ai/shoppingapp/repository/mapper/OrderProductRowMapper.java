package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;


import ai.shoppingapp.repository.entity.Product;

public class OrderProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		

		LocalDateTime createdAt = rs.getTimestamp("created_at") != null
				? rs.getTimestamp("created_at").toLocalDateTime() : null;
		LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null
				? rs.getTimestamp("updated_at").toLocalDateTime() : null;
		
		
		
		return new Product(
				rs.getString("id"),
				rs.getString("category_id"),
				rs.getString("name"),
				rs.getString("description"),
				rs.getBigDecimal("price"),
				rs.getString("image"),
				rs.getBoolean("is_active"),
				createdAt,
				updatedAt,
				rs.getString("created_user_id"),
				rs.getString("updated_user_id"),
				rs.getBoolean("is_discount"),
				rs.getInt("discount_product"),
				rs.getObject("discount_duration", LocalDate.class)
				);
	}

}
