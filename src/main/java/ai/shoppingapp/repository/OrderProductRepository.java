package ai.shoppingapp.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.repository.entity.Product;

import ai.shoppingapp.repository.mapper.OrderProductRowMapper;



@Repository
public class OrderProductRepository {

	 private final JdbcTemplate jdbcTemplate;

	    public OrderProductRepository(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }
	
	    public Product findById(String id) {

	        String sql = """
	                SELECT
	                    p.id,
	                    p.category_id,
	                    c.name AS category_name,
	                    p.name,
	                    p.description,
	                    p.price,
	                    p.image,
	                    p.is_active,
	                    p.created_at,
	                    p.updated_at,
	                    p.created_user_id,
	                    p.updated_user_id,

	                    p.is_discount,
	                    p.discount_product,
	                    p.discount_duration,
	                    p.discount_price,
	                    p.final_price

	                FROM products p

	                LEFT JOIN categories c
	                    ON p.category_id = c.id

	                WHERE p.id = ?
	                """;

	        List<Product> products = jdbcTemplate.query(
	                sql,
	                new OrderProductRowMapper(),
	                id
	        );

	        return products.isEmpty()
	                ? null
	                : products.get(0);
	    }
}
