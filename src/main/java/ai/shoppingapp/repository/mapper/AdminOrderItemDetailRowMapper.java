package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ai.shoppingapp.model.AdminOrderItemDetailDto;

public class AdminOrderItemDetailRowMapper implements RowMapper<AdminOrderItemDetailDto> {

    @Override
    public AdminOrderItemDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminOrderItemDetailDto item = new AdminOrderItemDetailDto();
        item.setProductName(rs.getString("product_name"));
        item.setProductImage(rs.getString("product_image"));
        item.setColour(rs.getString("colour"));
        item.setSize(rs.getString("size"));
        item.setQuantity(rs.getInt("quantity"));
        item.setUnitPrice(rs.getBigDecimal("unit_price"));
        item.setSubtotal(rs.getBigDecimal("subtotal"));
        return item;
    }
}
