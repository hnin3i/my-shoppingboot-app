package ai.shoppingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.AdminOrderItemDetailDto;
import ai.shoppingapp.model.AdminOrderListDto;
import ai.shoppingapp.model.AdminPaymentUpdateDto;
import ai.shoppingapp.model.AdminOrderDetailDto;
import ai.shoppingapp.repository.AdminOrderRepository;

@Service
public class AdminOrderService {
	
	private final AdminOrderRepository repo;
	public AdminOrderService(AdminOrderRepository repo) {
		this.repo=repo;
	}
	
	public List<AdminOrderListDto> getAllOrders(String orderStatus, String paymentStatus) {
        return repo.findFilteredOrders(orderStatus, paymentStatus);
    }
	
	public AdminOrderDetailDto getOrderDetail(String orderId) {
        AdminOrderDetailDto orderDetail = repo.findOrderDetailById(orderId);
        List<AdminOrderItemDetailDto> items = repo.findOrderItemsByOrderId(orderId);
        orderDetail.setItems(items);
        return orderDetail;
    }

    public boolean updatePaymentAndOrderStatus(AdminPaymentUpdateDto updateDto) {
        int rows = repo.updateOrderStatusAndPaymentStatus(
                updateDto.getOrderId(),
                updateDto.getStatus(),
                updateDto.getPaymentStatus()
        );
        return rows > 0;
    }
}
