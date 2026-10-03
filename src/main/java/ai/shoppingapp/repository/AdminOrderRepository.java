package ai.shoppingapp.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ai.shoppingapp.model.AdminOrderDetailDto;
import ai.shoppingapp.model.AdminOrderItemDetailDto;
import ai.shoppingapp.model.AdminOrderListDto;
import ai.shoppingapp.model.OrderStatus;
import ai.shoppingapp.model.PaymentStatus;
import ai.shoppingapp.repository.mapper.AdminOrderListRowMapper;
import ai.shoppingapp.repository.mapper.AdminLowStockRowMapper;
import ai.shoppingapp.repository.mapper.AdminOrderDetailRowMapper;
import ai.shoppingapp.repository.mapper.AdminOrderItemDetailRowMapper;

@Repository
public class AdminOrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AdminOrderListDto> findFilteredOrders(String customerName, String orderStatus, String paymentStatus, String startDate, String endDate) {
        StringBuilder sql = new StringBuilder(
            "SELECT o.id, o.order_number, u.name AS customer_name, o.phone_no, " +
            "o.created_at, o.total_amount, o.payment_status, o.status, o.payment_confirm_photo " +
            "FROM orders o " +
            "LEFT JOIN users u ON o.user_id = u.id WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (customerName != null && !customerName.trim().isEmpty()) {
            sql.append(" AND LOWER(u.name) LIKE LOWER(?) ");
            params.add("%" + customerName.trim() + "%");
        }

        if (orderStatus != null && !orderStatus.trim().isEmpty()) {
            sql.append(" AND o.status = ? ");
            params.add(orderStatus.trim());
        }

        if (paymentStatus != null && !paymentStatus.trim().isEmpty()) {
            sql.append(" AND o.payment_status = ? ");
            params.add(paymentStatus.trim());
        }

        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND o.created_at >= ? ");
            params.add(startDate.trim() + " 00:00:00");
        }

        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND o.created_at <= ? ");
            params.add(endDate.trim() + " 23:59:59");
        }

        sql.append(" ORDER BY o.created_at DESC");

        return jdbcTemplate.query(sql.toString(), new AdminOrderListRowMapper(), params.toArray());
    }

    public AdminOrderDetailDto findOrderDetailById(String orderId) {
        String sql = "SELECT o.id, o.order_number, o.created_at, u.name AS customer_name, o.phone_no, " +
                     "o.shipping_address, o.subtotal_amount, o.tax_amount, o.shipping_fee, " +
                     "o.total_amount, o.payment_method, o.payment_confirm_photo, o.payment_status, o.status " +
                     "FROM orders o " +
                     "LEFT JOIN users u ON o.user_id = u.id " +
                     "WHERE o.id = ?";

        return jdbcTemplate.queryForObject(sql, new AdminOrderDetailRowMapper(), orderId);
    }

    public List<AdminOrderItemDetailDto> findOrderItemsByOrderId(String orderId) {
        String sql = "SELECT p.name AS product_name, p.image AS product_image, " +
                     "s.colour, s.size, oi.quantity, oi.price AS unit_price, oi.subtotal " +
                     "FROM order_items oi " +
                     "JOIN stocks s ON oi.stock_id = s.id " +
                     "JOIN products p ON s.product_id = p.id " +
                     "WHERE oi.order_id = ?";

        return jdbcTemplate.query(sql, new AdminOrderItemDetailRowMapper(), orderId);
    }

    public int updateOrderStatusAndPaymentStatus(String orderId, OrderStatus status, PaymentStatus paymentStatus) {
        String sql = "UPDATE orders SET status = ?, payment_status = ?, updated_at = ? WHERE id = ?";
        return jdbcTemplate.update(
            sql, 
            status.name(), 
            paymentStatus.name(), 
            Timestamp.from(Instant.now()), 
            orderId
        );
    }

    public int deductStockByOrderId(String orderId) {
        String sql = "UPDATE stocks s " +
                     "JOIN order_items oi ON s.id = oi.stock_id " +
                     "SET s.stock_qty = s.stock_qty - oi.quantity " +
                     "WHERE oi.order_id = ?";
        return jdbcTemplate.update(sql, orderId);
    }
    
    public List<String> findLowStockProductsByOrderId(String orderId, int threshold) {
        String sql = "SELECT p.name AS product_name, s.colour, s.size, s.stock_qty " +
                     "FROM order_items oi " +
                     "JOIN stocks s ON oi.stock_id = s.id " +
                     "JOIN products p ON s.product_id = p.id " +
                     "WHERE oi.order_id = ? AND s.stock_qty < ?";

        return jdbcTemplate.query(sql, new AdminLowStockRowMapper(), orderId, threshold);
    }
}