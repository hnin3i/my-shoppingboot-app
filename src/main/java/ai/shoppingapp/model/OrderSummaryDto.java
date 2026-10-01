package ai.shoppingapp.model;

import java.math.BigDecimal;

public class OrderSummaryDto {
	
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal tax;
	private BigDecimal grandTotal;
	
	public OrderSummaryDto() {}
	public OrderSummaryDto(BigDecimal subtotal, BigDecimal shippingFee, BigDecimal tax, BigDecimal grandTotal) {
        this.subtotal = subtotal;
        this.shippingFee = shippingFee;
        this.tax = tax;
        this.grandTotal = grandTotal;
    }
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}
	

}
