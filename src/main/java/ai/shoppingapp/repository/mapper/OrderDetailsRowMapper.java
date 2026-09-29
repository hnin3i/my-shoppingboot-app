package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ai.shoppingapp.repository.entity.OrderDetailsEntity;

public class OrderDetailsRowMapper implements RowMapper<OrderDetailsEntity> {

    @Override
    public OrderDetailsEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDetailsEntity item = new OrderDetailsEntity();
        item.setProductName(rs.getString("product_name"));
        item.setImage(rs.getString("image"));
        item.setColour(rs.getString("colour"));
        item.setSize(rs.getString("size"));
        item.setPrice(rs.getDouble("price"));
        item.setQuantity(rs.getInt("quantity"));
        item.setSubtotal(rs.getDouble("subtotal"));
        return item;
        
       
      
    }
}
