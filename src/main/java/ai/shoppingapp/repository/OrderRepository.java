package ai.shoppingapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.shoppingapp.repository.entity.Order1;

@Repository
public class OrderRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public OrderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public int save(Order1 entity) {
		String sql="INSERT INTO orders ("
				+ "id,"
				+ "user_id,"
				+ "order_number,"
				+ "subtotal_amount,"
				+ "tax_amount,"
				+ "shipping_fee,"
				+ "total_amount,"
				+ "status,"
				+ "payment_method,"
				+ "payment_status,"
				+ "shipping_address,"
				+ "phone_no,"
				+ "created_at,"
				+ "updated_at,"
				+ "payment_confirm_photo,"
				+ "additional_note) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql,
				entity.getId(),
				entity.getUser_id(),
				entity.getOrder_number(),
				entity.getSubtotal_amount(),
				entity.getTax_amount(),
				entity.getShipping_fee(),
				entity.getTotal_amount(),
				entity.getStatus(),
				entity.getPayment_method(),
				entity.getPayment_status(),
				entity.getShipping_address(),
				entity.getPhone_no(),
				entity.getCreated_at(),
				entity.getUpdated_at(),
				entity.getPayment_confirm_photo(),
				entity.getAdditional_note());
	}

}
