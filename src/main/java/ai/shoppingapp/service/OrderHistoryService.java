package ai.shoppingapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ai.shoppingapp.model.OrderHistoryDto;
import ai.shoppingapp.model.OrderDetailsDto;
import ai.shoppingapp.model.OrderSummaryDto;
import ai.shoppingapp.repository.OrderHistoryRepository;
import ai.shoppingapp.repository.entity.OrderHistoryEntity;
import ai.shoppingapp.repository.entity.OrderDetailsEntity;

@Service
public class OrderHistoryService {
	
	private final OrderHistoryRepository repo;
	public OrderHistoryService(OrderHistoryRepository repo) {
		this.repo=repo;
	}
	
	public List<OrderHistoryDto> getOrderHistory(String userId) {
       List<OrderHistoryEntity> entities = repo.findOrderHistoryByUserId(userId);
       return entities.stream().map(this::toOrderDto).toList();
        		
    }
	public List<OrderDetailsDto> getOrderDetails(String userId,String orderId) {
		List<OrderDetailsEntity> entities = repo.findOrderItemsByOrderId(userId,orderId);
	    return entities.stream().map(this::toItemDto).toList();
	}
	public OrderSummaryDto getOrderSummary(String userId, String orderId) {
        OrderHistoryEntity order = repo.findOrderByIdAndUserId(userId, orderId);
        if (order == null) return null;

        List<OrderDetailsDto> items = getOrderDetails(userId, orderId);
        
        double calculatedSubtotal = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        double shippingFee = order.getShipping_fee();
        double taxRate = order.getTax_amount();
        double taxAmount = calculatedSubtotal*(taxRate/100.0);
        double grandTotal = calculatedSubtotal + shippingFee + taxAmount;

        return new OrderSummaryDto(calculatedSubtotal, shippingFee, taxAmount, grandTotal);
    }
	
	private OrderDetailsDto toItemDto(OrderDetailsEntity entity) {
        if (entity == null) return null;
        
        OrderDetailsDto dto = new OrderDetailsDto();
        dto.setProductName(entity.getProductName());
        dto.setImage(entity.getImage());
        dto.setColor(entity.getColour());
        dto.setSize(entity.getSize());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }
	
	private OrderHistoryDto toOrderDto(OrderHistoryEntity entity) {
        if (entity == null) return null;
        
        OrderHistoryDto dto = new OrderHistoryDto();
        dto.setOrderId(entity.getId());
        dto.setOrderNumber(entity.getOrder_number());           
        dto.setCustomerName(entity.getCustomerName());
        dto.setProductNames(entity.getProductName());
        dto.setTotalAmount(entity.getTotal_amount());            
        dto.setOrderStatus(entity.getStatus());           
        dto.setPaymentStatus(entity.getPayment_status());
        if (entity.getCreated_at() != null) {
            dto.setCreatedAt(java.sql.Timestamp.valueOf(entity.getCreated_at()));
        }
        return dto;
    }

}
