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

	// LIST (joined with products for display name)
	public List<Stock> findAllWithProduct() {

		String sql = """
				SELECT s.*, p.name AS product_name
				FROM stocks s
				JOIN products p ON s.products_id = p.id
				ORDER BY p.name ASC
				""";

		return this.jdbcTemplate.query(sql, new StockMapper());
	}

	// DETAIL / EDIT (joined, so the edit page can also show the product name)
	public Stock findById(String id) {

		String sql = """
				SELECT s.*, p.name AS product_name
				FROM stocks s
				JOIN products p ON s.products_id = p.id
				WHERE s.id = ?
				""";

		List<Stock> entities = this.jdbcTemplate.query(sql, new StockMapper(), id);

		return entities.isEmpty() ? null : entities.get(0);
	}

	// CREATE
	public int save(Stock entity) {

		String sql = """
				INSERT INTO stocks (id, stock_qty, products_id)
				VALUES (?, ?, ?)
				""";

		String id = UUID.randomUUID().toString();

		return this.jdbcTemplate.update(
				sql,
				id,
				entity.getStock_qty(),
				entity.getProducts_id()
		);
	}

	// UPDATE
	public int edit(String id, Stock entity) {

		String sql = """
				UPDATE stocks
				SET stock_qty = ?,
				    products_id = ?
				WHERE id = ?
				""";

		return this.jdbcTemplate.update(
				sql,
				entity.getStock_qty(),
				entity.getProducts_id(),
				id
		);
	}

	// DELETE - HARD DELETE
	public int delete(String id) {

		String sql = """
				DELETE FROM stocks
				WHERE id = ?
				""";

		return this.jdbcTemplate.update(sql, id);
	}

	// PRODUCT DROPDOWN (id + name only - read-only helper, not full Product CRUD)
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

	// STAT: total quantity across all stock rows
	public int getTotalQuantity() {

		String sql = "SELECT COALESCE(SUM(stock_qty), 0) FROM stocks";

		Integer total = this.jdbcTemplate.queryForObject(sql, Integer.class);

		return total == null ? 0 : total;
	}

	// STAT: number of distinct products that have a stock record
	public int getDistinctProductCount() {

		String sql = "SELECT COUNT(DISTINCT products_id) FROM stocks";

		Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class);

		return count == null ? 0 : count;
	}

	// STAT: number of distinct categories represented among stocked products
	public int getDistinctCategoryCount() {

		String sql = """
				SELECT COUNT(DISTINCT p.categories_id)
				FROM stocks s
				JOIN products p ON s.products_id = p.id
				""";

		Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class);

		return count == null ? 0 : count;
	}

	// STAT: number of stock rows at or below the given threshold
	public int getLowStockCount(int threshold) {

		String sql = "SELECT COUNT(*) FROM stocks WHERE stock_qty < ?";

		Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, threshold);

		return count == null ? 0 : count;
	}
}
