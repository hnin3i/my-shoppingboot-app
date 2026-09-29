package ai.shoppingapp.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ai.shoppingapp.repository.entity.OrderHistoryEntity;
import ai.shoppingapp.repository.entity.OrderDetailsEntity;
import ai.shoppingapp.repository.mapper.OrderDetailsRowMapper;
import ai.shoppingapp.repository.mapper.OrderHistoryRowMapper;

@Repository
public class OrderHistoryRepository {
	
private final JdbcTemplate jdbcTemplate;

	public OrderHistoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public List<OrderHistoryEntity> findOrderHistoryByUserId(String userId) {
	    String sql = "SELECT o.id AS order_id, o.order_number, u.name AS customer_name, "
	            + "GROUP_CONCAT(p.name SEPARATOR ', ') AS product_names, "
	            + "o.total_amount, o.tax_amount, o.shipping_fee, o.status AS order_status, "
	            + "o.payment_status, o.created_at "
	            + "FROM orders o "
	            + "JOIN users u ON o.user_id = u.id "
	            + "JOIN order_items oi ON o.id = oi.order_id "
	            + "JOIN stocks s ON oi.stock_id = s.id "
	            + "JOIN products p ON s.product_id = p.id "
	            + "WHERE o.user_id = ? "
	            + "GROUP BY o.id, o.order_number, u.name, o.total_amount, o.tax_amount, o.shipping_fee, o.status, o.payment_status, o.created_at "
	            + "ORDER BY o.created_at DESC";

	    return jdbcTemplate.query(sql, new OrderHistoryRowMapper(), userId);
	}
	public List<OrderDetailsEntity> findOrderItemsByOrderId(String userId,String orderId) {
		String sql = "SELECT p.name AS product_name, p.image, s.colour, s.size, " +
                "oi.price, oi.quantity, (oi.price * oi.quantity) AS subtotal " +
                "FROM order_items oi " +
                "JOIN stocks s ON oi.stock_id = s.id " +
                "JOIN products p ON s.product_id = p.id " +
                "JOIN orders o ON oi.order_id = o.id " +
                "WHERE o.user_id = ? AND o.id = ?";
        return jdbcTemplate.query(sql, new OrderDetailsRowMapper(),userId, orderId);
    }
	public OrderHistoryEntity findOrderByIdAndUserId(String userId, String orderId) {
        String sql = "SELECT o.id AS order_id, o.order_number, u.name AS customer_name, '' AS product_names, \r\n"
        		+ "o.subtotal_amount, o.tax_amount, o.shipping_fee, o.total_amount, \r\n"
        		+ "o.status AS order_status, o.payment_status, o.created_at \r\n"
        		+ "FROM orders o \r\n"
        		+ "JOIN users u ON o.user_id = u.id \r\n"
        		+ "WHERE o.user_id = ? AND o.id = ?";

        List<OrderHistoryEntity> list = jdbcTemplate.query(sql, new OrderHistoryRowMapper(), userId, orderId);
        return list.isEmpty() ? null : list.get(0);
    }
	

}
