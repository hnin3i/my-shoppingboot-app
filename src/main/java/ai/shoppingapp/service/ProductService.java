package ai.shoppingapp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<ProductModel> findAll() {

		return productRepository.findAll();
	}

	public List<ProductModel> search(String keyword) {

		if (keyword == null || keyword.trim().isEmpty()) {

			return findAll();
		}

		return productRepository.search(keyword.trim());
	}

	public ProductModel findById(String id) {

		return productRepository.findById(id);
	}

	public void save(ProductModel product) {

		if (product.getId() == null || product.getId().isEmpty()) {

			product.setId(UUID.randomUUID().toString());
		}

		calculatePrice(product);

		productRepository.save(product);
	}

	public void update(ProductModel product) {

		calculatePrice(product);

		productRepository.update(product);
	}

	public void delete(String id) {

		productRepository.delete(id);
	}

	private void calculatePrice(ProductModel product) {

		BigDecimal price = product.getPrice();

		if (price == null) {

			product.setDiscountPrice(BigDecimal.ZERO);

			product.setFinalPrice(BigDecimal.ZERO);

			return;
		}

		if (product.getIsDiscount() != 1) {

			product.setDiscountProduct(0);

			product.setDiscountPrice(BigDecimal.ZERO);

			product.setFinalPrice(BigDecimal.ZERO);
			
			product.setDiscountDuration(null);

			return;
		}

		BigDecimal discountAmount =
				product.getDiscountPrice();

		if (discountAmount.compareTo(price) > 0) {

			discountAmount = price;
			product.setDiscountPrice(price);
		}

		product.setDiscountProduct(1);

		BigDecimal salePrice = price.subtract(discountAmount);
		product.setFinalPrice(salePrice);
	}
}