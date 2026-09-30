package ai.shoppingapp.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.repository.entity.User;
import ai.shoppingapp.repository.mapper.UserMapper;

/**
 * Scope: admin user listing + role management only (my assigned task).
 * Login/Register/ChangePassword queries belong to the auth teammate's task
 * and can be added here later without touching the methods below.
 */
@Repository
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<User> findAll() {
		String sql = "SELECT * FROM users ORDER BY created_at DESC";
		return this.jdbcTemplate.query(sql, new UserMapper());
	}

	public User findById(String id) {
		String sql = "SELECT * FROM users WHERE id = ?";
		List<User> entities = this.jdbcTemplate.query(sql, new UserMapper(), id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int updateRole(String id, String role) {
		String sql = "UPDATE users SET role = ?, updated_at = NOW() WHERE id = ?";
		return this.jdbcTemplate.update(sql, role, id);
	}
}
