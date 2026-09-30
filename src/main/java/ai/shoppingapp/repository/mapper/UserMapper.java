package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import ai.shoppingapp.repository.entity.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		LocalDateTime createdAt = rs.getTimestamp("created_at") != null
				? rs.getTimestamp("created_at").toLocalDateTime() : null;
		LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null
				? rs.getTimestamp("updated_at").toLocalDateTime() : null;

		return new User(
				rs.getString("id"),
				rs.getString("name"),
				rs.getString("email"),
				rs.getString("phone"),
				rs.getString("password"),
				rs.getString("role"),
				rs.getString("address"),
				createdAt,
				updatedAt,
				rs.getString("profile")
				);
	}

}
