package ai.shoppingapp.repository.entity;

public class Stock {
	private String id;
	private int stock_qty;
	private String products_id;
	
	public Stock() {}
	public Stock(String id,int stock_qty,String products_id) {
		this.id=id;
		this.stock_qty=stock_qty;
		this.products_id=products_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getStock_qty() {
		return stock_qty;
	}
	public void setStock_qty(int stock_qty) {
		this.stock_qty = stock_qty;
	}
	public String getProducts_id() {
		return products_id;
	}
	public void setProducts_id(String products_id) {
		this.products_id = products_id;
	}
	
	

}
