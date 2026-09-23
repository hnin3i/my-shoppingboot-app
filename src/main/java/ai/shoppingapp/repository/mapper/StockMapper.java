package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import ai.shoppingapp.repository.entity.Stock;

public class StockMapper implements RowMapper<Stock> {

	@Override
	public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {

		LocalDateTime createdAt = rs.getTimestamp("created_at") != null
				? rs.getTimestamp("created_at").toLocalDateTime() : null;
		LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null
				? rs.getTimestamp("updated_at").toLocalDateTime() : null;

		Stock stock = new Stock(
				rs.getString("id"),
				rs.getString("product_id"),
				rs.getString("colour"),
				rs.getString("size"),
				rs.getInt("stock_qty"),
				createdAt,
				updatedAt
		);

		// Present only when the query joins the products table (SELECT ... p.name AS product_name)
		String productName = hasColumn(rs, "product_name") ? rs.getString("product_name") : null;
		stock.setProductName(productName);

		return stock;
	}

	private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		int columns = rs.getMetaData().getColumnCount();
		for (int i = 1; i <= columns; i++) {
			if (rs.getMetaData().getColumnLabel(i).equalsIgnoreCase(columnName)) {
				return true;
			}
		}
		return false;
	}
}
