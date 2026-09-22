package ai.shoppingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.StockModel;
import ai.shoppingapp.repository.StockRepository;
import ai.shoppingapp.repository.entity.Product;
import ai.shoppingapp.repository.entity.Stock;

@Service
public class StockService {

	// Fixed low-stock cutoff (no threshold column in the database yet).
	// A quantity strictly below this value is flagged as "Low Stock".
	private static final int LOW_STOCK_THRESHOLD = 5;

	private final StockRepository repo;

	public StockService(StockRepository repo) {
		this.repo = repo;
	}

	// LIST
	public List<StockModel> findAll() {

		List<Stock> entities = this.repo.findAllWithProduct();

		return entities.stream().map(this::toDto).toList();
	}

	// DETAIL / EDIT
	public StockModel findById(String id) {

		Stock entity = this.repo.findById(id);

		return entity == null ? null : toDto(entity);
	}

	// CREATE
	public int add(StockModel dto) {

		Stock entity = toEntity(dto);

		return this.repo.save(entity);
	}

	// UPDATE
	public int edit(String id, StockModel dto) {

		Stock entity = toEntity(dto);

		return this.repo.edit(id, entity);
	}

	// DELETE
	public int delete(String id) {

		return this.repo.delete(id);
	}

	// Product dropdown options for the add/edit forms
	public List<Product> getProductOptions() {

		return this.repo.findAllProductsForDropdown();
	}

	// STATS for the list page cards
	public int getTotalQuantity() {
		return this.repo.getTotalQuantity();
	}

	public int getProductCount() {
		return this.repo.getDistinctProductCount();
	}

	public int getCategoryCount() {
		return this.repo.getDistinctCategoryCount();
	}

	public int getLowStockCount() {
		return this.repo.getLowStockCount(LOW_STOCK_THRESHOLD);
	}

	// Entity -> DTO
	private StockModel toDto(Stock entity) {

		StockModel dto = new StockModel();

		dto.setId(entity.getId());
		dto.setStockQty(entity.getStock_qty());
		dto.setProductsId(entity.getProducts_id());
		dto.setProductName(entity.getProductName());
		dto.setLowStock(entity.getStock_qty() < LOW_STOCK_THRESHOLD);

		return dto;
	}

	// DTO -> Entity
	private Stock toEntity(StockModel dto) {

		Stock entity = new Stock();

		entity.setId(dto.getId());
		entity.setStock_qty(dto.getStockQty());
		entity.setProducts_id(dto.getProductsId());

		return entity;
	}
}
