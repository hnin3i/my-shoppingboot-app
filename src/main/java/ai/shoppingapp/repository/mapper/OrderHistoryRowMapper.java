package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import ai.shoppingapp.repository.entity.OrderHistoryEntity;

public class OrderHistoryRowMapper implements RowMapper<OrderHistoryEntity>  {
	
	@Override
    public OrderHistoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderHistoryEntity entity = new OrderHistoryEntity();
		entity.setId(rs.getString("order_id"));
		entity.setOrder_number(rs.getString("order_number"));
		entity.setCustomerName(rs.getString("customer_name"));
		entity.setProductName(rs.getString("product_names"));
		entity.setTotal_amount(rs.getDouble("total_amount"));
		entity.setTax_amount(rs.getDouble("tax_amount"));
		entity.setShipping_fee(rs.getDouble("shipping_fee"));
		entity.setStatus(rs.getString("order_status"));
		entity.setPayment_status(rs.getString("payment_status"));
		if(rs.getTimestamp("created_at")!=null) {
			entity.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
		}
        return entity;
    }

}
