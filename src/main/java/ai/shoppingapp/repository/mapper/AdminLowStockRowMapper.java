package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdminLowStockRowMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        String name = rs.getString("product_name");
        String colour = rs.getString("colour");
        String size = rs.getString("size");
        int qty = rs.getInt("stock_qty");

        StringBuilder spec = new StringBuilder(name);
        if ((colour != null && !colour.isBlank()) || (size != null && !size.isBlank())) {
            spec.append(" (")
                .append(colour != null ? colour : "")
                .append(size != null ? "/" + size : "")
                .append(")");
        }
        spec.append(" - remain quantity:").append(qty).append(" ");

        return spec.toString();
    }
}