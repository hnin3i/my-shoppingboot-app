package ai.shoppingapp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.repository.entity.Product;
import ai.shoppingapp.repository.entity.Stock;
import ai.shoppingapp.repository.mapper.StockMapper;

@Repository
public class StockRepository {

private final JdbcTemplate jdbcTemplate;

public StockRepository(JdbcTemplate jdbcTemplate) {
this.jdbcTemplate = jdbcTemplate;
}

// LIST (is_active = 1 ဖြစ်သော Active Stock များကိုသာ ထုတ်ပြမည်)
public List<Stock> findAllWithProduct() {

String sql = """
SELECT s.*, p.name AS product_name
FROM stocks s
JOIN products p ON s.product_id = p.id
WHERE s.is_active = 1
ORDER BY p.name ASC
""";

return this.jdbcTemplate.query(sql, new StockMapper());
}

// DETAIL / EDIT (is_active = 1 ဖြစ်သော Stock ကိုသာ စစ်ဆေးမည်)
public Stock findById(String id) {

String sql = """
SELECT s.*, p.name AS product_name
FROM stocks s
JOIN products p ON s.product_id = p.id
WHERE s.id = ? AND s.is_active = 1
""";

List<Stock> entities = this.jdbcTemplate.query(sql, new StockMapper(), id);

return entities.isEmpty() ? null : entities.get(0);
}

// CREATE (is_active = 1 ဖြင့် အသစ်ထည့်သွင်းမည်)
public int save(Stock entity) {

String sql = """
INSERT INTO stocks (id, product_id, colour, size, stock_qty, is_active, created_at, updated_at)
VALUES (?, ?, ?, ?, ?, 1, NOW(), NOW())
""";

String id = UUID.randomUUID().toString();

return this.jdbcTemplate.update(
sql,
id,
entity.getProduct_id(),
entity.getColour(),
entity.getSize(),
entity.getStock_qty()
);
}

// UPDATE
public int edit(String id, Stock entity) {

String sql = """
UPDATE stocks
SET product_id = ?,
colour = ?,
size = ?,
stock_qty = ?,
updated_at = NOW()
WHERE id = ? AND is_active = 1
""";

return this.jdbcTemplate.update(
sql,
entity.getProduct_id(),
entity.getColour(),
entity.getSize(),
entity.getStock_qty(),
id
);
}

// DELETE - SOFT DELETE (is_active = 0 သို့ ပြောင်းလဲခြင်း)
public int delete(String id) {

String sql = """
UPDATE stocks
SET is_active = 0,
updated_at = NOW()
WHERE id = ?
""";

return this.jdbcTemplate.update(sql, id);
}

// PRODUCT DROPDOWN (id + name only)
public List<Product> findAllProductsForDropdown() {

String sql = """
SELECT id, name
FROM products
ORDER BY name ASC
""";

return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
Product product = new Product();
product.setId(rs.getString("id"));
product.setName(rs.getString("name"));
return product;
});
}

// STAT: total quantity across active stock rows
public int getTotalQuantity() {

String sql = "SELECT COALESCE(SUM(stock_qty), 0) FROM stocks WHERE is_active = 1";

Integer total = this.jdbcTemplate.queryForObject(sql, Integer.class);

return total == null ? 0 : total;
}

// STAT: number of distinct products that have an active stock record
public int getDistinctProductCount() {

String sql = "SELECT COUNT(DISTINCT product_id) FROM stocks WHERE is_active = 1";

Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class);

return count == null ? 0 : count;
}

// STAT: number of distinct categories represented among active stocked products
public int getDistinctCategoryCount() {

String sql = """
SELECT COUNT(DISTINCT p.category_id)
FROM stocks s
JOIN products p ON s.product_id = p.id
WHERE s.is_active = 1
""";

Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class);

return count == null ? 0 : count;
}

// STAT: number of active stock rows at or below the given threshold
public int getLowStockCount(int threshold) {

String sql = "SELECT COUNT(*) FROM stocks WHERE stock_qty < ? AND is_active = 1";

Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, threshold);

		return count == null ? 0 : count;
	}
	
//	public int decreaseStock(String stockId,int quantity) {
//		String sql="UPDATE stocks\r\n"
//				+ "                SET stock_qty = stock_qty - ?,\r\n"
//				+ "                    updated_at = NOW()\r\n"
//				+ "                WHERE id = ?\r\n"
//				+ "                  AND stock_qty >= ?";
//		
//		Integer count=this.jdbcTemplate.update(sql,quantity,stockId,quantity);
//		return count==null?0:count;
//	}
	
	public List<Stock> findByProductId(String productId) { 
		String sql = "SELECT s.*, p.name AS product_name "
				+ "FROM stocks s JOIN products p ON s.product_id = p.id "
				+ "WHERE s.product_id = ? "
				+ "ORDER BY s.colour ASC, s.size ASC";
       return this.jdbcTemplate.query( sql, new StockMapper(), productId );
       
	}
}