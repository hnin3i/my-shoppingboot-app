package ai.shoppingapp.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.model.ProductModel;
import ai.shoppingapp.repository.mapper.ProductMapper;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductModel> findAll() {

        String sql = """
                SELECT
                    p.id,
                    p.category_id,
                    c.name AS category_name,
                    p.name,
                    p.description,
                    p.price,
                    p.image,
                    p.is_active,
                    p.created_at,
                    p.updated_at,
                    p.created_user_id,
                    p.updated_user_id,

                    p.is_discount,
                    p.discount_product,
                    p.discount_duration,
                    p.discount_price,
                    p.final_price

                FROM products p

                LEFT JOIN categories c
                    ON p.category_id = c.id

                ORDER BY p.created_at DESC
                """;

        return jdbcTemplate.query(
                sql,
                new ProductMapper()
        );
    }

    public List<ProductModel> search(String keyword) {

        String sql = """
                SELECT
                    p.id,
                    p.category_id,
                    c.name AS category_name,
                    p.name,
                    p.description,
                    p.price,
                    p.image,
                    p.is_active,
                    p.created_at,
                    p.updated_at,
                    p.created_user_id,
                    p.updated_user_id,

                    p.is_discount,
                    p.discount_product,
                    p.discount_duration,
                    p.discount_price,
                    p.final_price

                FROM products p

                LEFT JOIN categories c
                    ON p.category_id = c.id

                WHERE LOWER(p.name) LIKE LOWER(?)
                   OR LOWER(c.name) LIKE LOWER(?)

                ORDER BY p.created_at DESC
                """;

        String searchKeyword = "%" + keyword + "%";

        return jdbcTemplate.query(
                sql,
                new ProductMapper(),
                searchKeyword,
                searchKeyword
        );
    }

    public ProductModel findById(String id) {

        String sql = """
                SELECT
                    p.id,
                    p.category_id,
                    c.name AS category_name,
                    p.name,
                    p.description,
                    p.price,
                    p.image,
                    p.is_active,
                    p.created_at,
                    p.updated_at,
                    p.created_user_id,
                    p.updated_user_id,

                    p.is_discount,
                    p.discount_product,
                    p.discount_duration,
                    p.discount_price,
                    p.final_price

                FROM products p

                LEFT JOIN categories c
                    ON p.category_id = c.id

                WHERE p.id = ?
                """;

        List<ProductModel> products = jdbcTemplate.query(
                sql,
                new ProductMapper(),
                id
        );

        return products.isEmpty()
                ? null
                : products.get(0);
    }

    public int save(ProductModel product) {

        String sql = """
                INSERT INTO products
                (
                    id,
                    category_id,
                    name,
                    description,
                    price,
                    image,
                    is_active,

                    created_at,
                    updated_at,
                    created_user_id,
                    updated_user_id,

                    is_discount,
                    discount_product,
                    discount_duration,
                    discount_price,
                    final_price
                )

                VALUES
                (
                    ?,
                    ?,
                    ?,
                    ?,
                    ?,
                    ?,
                    ?,

                    NOW(),
                    NOW(),
                    ?,
                    ?,

                    ?,
                    ?,
                    ?,
                    ?,
                    ?
                )
                """;

        return jdbcTemplate.update(
                sql,

                product.getId(),

                product.getCategoryId(),

                product.getName(),

                product.getDescription(),

                product.getPrice(),

                product.getImage(),

                product.getIsActive(),

                product.getCreatedUserId(),

                product.getUpdatedUserId(),

                product.getIsDiscount(),

                product.getDiscountProduct(),

                product.getDiscountDuration(),

                product.getDiscountPrice(),

                product.getFinalPrice()
        );
    }

    public int update(ProductModel product) {

        String sql = """
                UPDATE products

                SET
                    category_id = ?,
                    name = ?,
                    description = ?,
                    price = ?,
                    image = ?,
                    is_active = ?,

                    updated_at = NOW(),
                    updated_user_id = ?,

                    is_discount = ?,
                    discount_product = ?,
                    discount_duration = ?,
                    discount_price = ?,
                    final_price = ?

                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,

                product.getCategoryId(),

                product.getName(),

                product.getDescription(),

                product.getPrice(),

                product.getImage(),

                product.getIsActive(),

                product.getUpdatedUserId(),

                product.getIsDiscount(),

                product.getDiscountProduct(),

                product.getDiscountDuration(),

                product.getDiscountPrice(),

                product.getFinalPrice(),

                product.getId()
        );
    }

    public int delete(String id) {

        String sql = """
                DELETE FROM products
                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,
                id
        );
    }
}