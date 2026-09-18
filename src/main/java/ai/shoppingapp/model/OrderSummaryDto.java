package ai.shoppingapp.model;

public class OrderSummaryDto {
	
	private double subtotal;
	private double shippingFee;
	private double tax;
	private double grandTotal;
	
	public OrderSummaryDto() {}
	public OrderSummaryDto(double subtotal, double shippingFee, double tax, double grandTotal) {
        this.subtotal = subtotal;
        this.shippingFee = shippingFee;
        this.tax = tax;
        this.grandTotal = grandTotal;
    }
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	

}
