package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ai.shoppingapp.model.AdminOrderListDto;
import ai.shoppingapp.model.OrderStatus;
import ai.shoppingapp.model.PaymentStatus;

public class AdminOrderListRowMapper implements RowMapper<AdminOrderListDto> {

    @Override
    public AdminOrderListDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminOrderListDto dto = new AdminOrderListDto();
        dto.setId(rs.getString("id"));
        dto.setOrderNumber(rs.getString("order_number"));
        dto.setCustomerName(rs.getString("customer_name"));
        dto.setCustomerPhone(rs.getString("phone_no"));
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setTotalAmount(rs.getDouble("total_amount"));
        dto.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")));
        dto.setStatus(OrderStatus.valueOf(rs.getString("status")));
        dto.setPaymentConfirmPhoto(rs.getString("payment_confirm_photo"));
        return dto;
    }
}
