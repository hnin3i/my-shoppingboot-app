package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ai.shoppingapp.model.AdminOrderDetailDto;
import ai.shoppingapp.model.OrderStatus;
import ai.shoppingapp.model.PaymentStatus;

public class AdminOrderDetailRowMapper implements RowMapper<AdminOrderDetailDto> {

    @Override
    public AdminOrderDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminOrderDetailDto dto = new AdminOrderDetailDto();
        dto.setId(rs.getString("id"));
        dto.setOrderNumber(rs.getString("order_number"));
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setCustomerName(rs.getString("customer_name"));
        dto.setCustomerPhone(rs.getString("phone_no"));
        dto.setShippingAddress(rs.getString("shipping_address"));
        dto.setSubtotalAmount(rs.getDouble("subtotal_amount"));
        dto.setTaxAmount(rs.getDouble("tax_amount"));
        dto.setShippingFee(rs.getDouble("shipping_fee"));
        dto.setTotalAmount(rs.getDouble("total_amount"));
        dto.setPaymentMethod(rs.getString("payment_method"));
        dto.setPaymentConfirmPhoto(rs.getString("payment_confirm_photo"));
        dto.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")));
        dto.setStatus(OrderStatus.valueOf(rs.getString("status")));
        return dto;
    }
}
