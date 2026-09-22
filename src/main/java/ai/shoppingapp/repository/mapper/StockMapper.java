package ai.shoppingapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ai.shoppingapp.repository.entity.Stock;

public class StockMapper implements RowMapper<Stock> {

	@Override
	public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {

		Stock stock = new Stock(
				rs.getString("id"),
				rs.getInt("stock_qty"),
				rs.getString("products_id")
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
