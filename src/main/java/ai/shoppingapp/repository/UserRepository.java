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
	public User findByEmail(String email) {
		String sql="SELECT * FROM users WHERE email=?";				
		List<User> entites=this.jdbcTemplate.query(sql, new UserMapper(),email);
		
		return entites.isEmpty()?null:entites.get(0);
	}
	public int save(User entity) {
		 String sql = "INSERT INTO users (id,username,email,phone,password,role,photo,status) "
		 		+ "VALUES (?, ?,?,?,?,?,?,?)";
		 return this.jdbcTemplate.update(sql,
				 entity.getId(),
				 entity.getName(),
				 entity.getEmail(),
				 entity.getPhone(),
				 entity.getPassword(),
				 entity.getRole(),
				 entity.getProfile()
				 );
	}
	public int changePassword(String id,String password) {
		 String sql = "UPDATE users SET password = ? WHERE id = ?";
		 return jdbcTemplate.update(sql, password,id);
	}
	public int changePasswordByEmail(String email, String password) {
	    String sql = "UPDATE users SET password = ? WHERE email = ?";
	    return jdbcTemplate.update(sql, password, email);
	}
	public int changeProfile(String id,String username,String phone,byte[]photo) {
		 String sql = "UPDATE users SET username = ?,phone=?,photo=? WHERE id = ?";
		 return jdbcTemplate.update(sql, username,phone,photo,id);
	}
	public int changeUserName(String id,String username) {
		 String sql = "UPDATE users SET username = ? WHERE id = ?";
		 return jdbcTemplate.update(sql, username,id);
	}
	public int changePhoto(String id,String profile) {
		 String sql = "UPDATE users SET profile = ? WHERE id = ?";
		 return jdbcTemplate.update(sql, profile,id);
	}

}
