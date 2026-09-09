package ai.shoppingapp.repository.entity;

public class OrderItem {

	private String id;
	private double price;
	private int quantity;
	private double subtotal;
	private String orders_id;
	private String products_id;
	
	public OrderItem() {}
	
	public OrderItem(String id,double price,int quantity,double subtotal,String orders_id,String products_id) {
		this.id=id;
		this.price=price;
		this.quantity=quantity;
		this.subtotal=subtotal;
		this.orders_id=orders_id;
		this.products_id=products_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public String getOrders_id() {
		return orders_id;
	}

	public void setOrders_id(String orders_id) {
		this.orders_id = orders_id;
	}

	public String getProducts_id() {
		return products_id;
	}

	public void setProducts_id(String products_id) {
		this.products_id = products_id;
	}
	
	
}
