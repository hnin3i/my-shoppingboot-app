package ai.shoppingapp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.repository.entity.Category;
import ai.shoppingapp.repository.mapper.CategoryMapper;

@Repository
public class CategoryRepository {

	private final JdbcTemplate jdbcTemplate;

	public CategoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

// LIST
	public List<Category> findAll() {

		String sql = "SELECT *FROM categories ORDER BY created_at DESC";

		List<Category> entities = this.jdbcTemplate.query(sql, new CategoryMapper());

		return entities;
	}

// DETAIL
	public Category findById(String id) {

		String sql = """
				SELECT *
				FROM categories
				WHERE id = ?
				""";

		Category entity = this.jdbcTemplate.queryForObject(sql, new CategoryMapper(), id);

		return entity;
	}

// CREATE
	public int save(Category entity) {

		String sql = """
				INSERT INTO categories
				(id, name, description, image, is_active,created_user_id,updated_user_id,created_at)
				VALUES (?, ?, ?, ?, ?,?,?, NOW())
				""";

		String id = UUID.randomUUID().toString();

		return this.jdbcTemplate.update(sql, id, entity.getName(), entity.getDescription(), entity.getImage(),
				entity.getIsActive(),entity.getCreatedUserId(),entity.getUpdatedUserId());
	}

// UPDATE
	public int edit(String id, Category entity) {

		String sql = """
				UPDATE categories
				SET name = ?,
				    description = ?,
				    image = ?,
				    is_active = ?,
				    updated_at = NOW()
				WHERE id = ?
				""";

		return this.jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getImage(),
				entity.getIsActive(), id);
	}

// DELETE
	public int delete(String id) {

		String sql = """
				UPDATE categories
				SET is_active = 0,
				    updated_at = NOW()
				WHERE id = ?
				""";

		return this.jdbcTemplate.update(sql, id);
	}

//CHECK DUPLICATE NAME FOR CREATE
public boolean existsByName(String name) {

 String sql = """
         SELECT COUNT(*)
         FROM categories
         WHERE name = ?
         """;

 Integer count = jdbcTemplate.queryForObject(
         sql,
         Integer.class,
         name
 );

 return count != null && count > 0;
}


//CHECK DUPLICATE NAME FOR UPDATE
public boolean existsByName(String name, String id) {

 String sql = """
         SELECT COUNT(*)
         FROM categories
         WHERE name = ?
         AND id != ?
         """;

 Integer count = jdbcTemplate.queryForObject(
         sql,
         Integer.class,
         name,
         id
 );

 return count != null && count > 0;
}
}