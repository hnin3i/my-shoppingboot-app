package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ai.shoppingapp.repository.entity.Category;

public class CategoryMapper implements RowMapper<Category> {

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

		return new Category(rs.getString("id"), rs.getString("name"), rs.getString("description"),
				rs.getString("image"), rs.getInt("is_active"), rs.getString("created_at"), rs.getString("updated_at"),
				rs.getString("created_user_id"), rs.getString("updated_user_id"));
	}
}