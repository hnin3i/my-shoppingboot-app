package ai.shoppingapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.repository.entity.OrderItem1;

@Repository
public class OrderItemRepository {

	private final JdbcTemplate jdbcTemplate;
	
	public OrderItemRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public int save(OrderItem1 entity) {
		String sql = "INSERT INTO order_items ("
				+ "id,"
				+ "order_id,"
				+ "stock_id,"
				+ "price,"
				+ "quantity,"
				+ "subtotal,"
				+ "created_at,"
				+ "updated_at) VALUES(?,?,?,?,?,?,?,?) ";
		return this.jdbcTemplate.update(sql,
				entity.getId(),
				entity.getOrder_id(),
				entity.getStock_id(),
				entity.getPrice(),
				entity.getQuantity(),
				entity.getSubtotal(),
				entity.getCreated_at(),
				entity.getUpdated_at());
	}
}
