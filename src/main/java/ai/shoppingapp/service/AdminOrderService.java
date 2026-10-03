package ai.shoppingapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.shoppingapp.model.AdminOrderItemDetailDto;
import ai.shoppingapp.model.AdminOrderListDto;
import ai.shoppingapp.model.AdminPaymentUpdateDto;
import ai.shoppingapp.model.OrderStatus;
import ai.shoppingapp.model.AdminOrderDetailDto;
import ai.shoppingapp.repository.AdminOrderRepository;

@Service
public class AdminOrderService {
	
	private final AdminOrderRepository repo;
	public AdminOrderService(AdminOrderRepository repo) {
		this.repo=repo;
	}
	
	public List<AdminOrderListDto> getAllOrders(String customerName, String orderStatus, String paymentStatus, String startDate, String endDate) {
        return repo.findFilteredOrders(customerName,orderStatus, paymentStatus,startDate,endDate);
    }
	
	public AdminOrderDetailDto getOrderDetail(String orderId) {
        AdminOrderDetailDto orderDetail = repo.findOrderDetailById(orderId);
        List<AdminOrderItemDetailDto> items = repo.findOrderItemsByOrderId(orderId);
        orderDetail.setItems(items);
        return orderDetail;
    }
 
	@Transactional 
	public List<String> updatePaymentAndOrderStatus(AdminPaymentUpdateDto updateDto) {
		List<String> lowStockWarnings = new ArrayList<>();
		
		// ၁။ လက်ရှိ Database ထဲရှိ Order Status ကို အရင် ဆွဲထုတ် စစ်ဆေးခြင်း
		AdminOrderDetailDto currentOrder = repo.findOrderDetailById(updateDto.getOrderId());
		
		if (currentOrder == null) {
			return lowStockWarnings;
		}

		OrderStatus oldStatus = currentOrder.getStatus();
		OrderStatus newStatus = updateDto.getStatus();

		if (oldStatus != OrderStatus.CONFIRMED && newStatus == OrderStatus.CONFIRMED) {
			repo.deductStockByOrderId(updateDto.getOrderId());
			
			lowStockWarnings = repo.findLowStockProductsByOrderId(updateDto.getOrderId(), 5);
		}

		repo.updateOrderStatusAndPaymentStatus(
				updateDto.getOrderId(),
				updateDto.getStatus(),
				updateDto.getPaymentStatus()
		);

		return lowStockWarnings;
	}
}
