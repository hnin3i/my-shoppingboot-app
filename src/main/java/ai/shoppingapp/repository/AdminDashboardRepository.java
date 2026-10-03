package ai.shoppingapp.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.model.AdminOrderListDto;
import ai.shoppingapp.repository.mapper.AdminOrderListRowMapper;

@Repository
public class AdminDashboardRepository {
	
	private final JdbcTemplate jdbcTemplate;

    public AdminDashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) FROM products";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int getTotalCategories() {
        String sql = "SELECT COUNT(*) FROM categories";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int getTotalStock() {
        String sql = "SELECT COALESCE(SUM(stock_qty), 0) FROM stocks WHERE is_active = 1";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
        return total != null ? total : 0;
    }

    public int getLowStockCount(int threshold) {
        String sql = "SELECT COUNT(*) FROM stocks WHERE stock_qty < ? AND is_active = 1";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, threshold);
        return count != null ? count : 0;
    }

    public List<AdminOrderListDto> findRecentOrders(int limit) {
        String sql = "SELECT o.id, o.order_number, u.name AS customer_name, o.phone_no, " +
                     "o.created_at, o.total_amount, o.payment_status, o.status, o.payment_confirm_photo " +
                     "FROM orders o " +
                     "LEFT JOIN users u ON o.user_id = u.id " +
                     "ORDER BY o.created_at DESC LIMIT ?";

        return jdbcTemplate.query(sql, new AdminOrderListRowMapper(), limit);
    }

}
