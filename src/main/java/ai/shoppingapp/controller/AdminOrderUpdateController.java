package ai.shoppingapp.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ai.shoppingapp.model.AdminOrderListDto;
import ai.shoppingapp.model.AdminPaymentUpdateDto;
import ai.shoppingapp.model.AdminOrderDetailDto;
import ai.shoppingapp.model.OrderStatus;
import ai.shoppingapp.model.PaymentStatus;
import ai.shoppingapp.service.AdminOrderService;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderUpdateController {
	
	private final AdminOrderService  service;

    public AdminOrderUpdateController(AdminOrderService service) {
        this.service = service;
    }
    
    @GetMapping
    public String showOrderListPage(
    		@RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "paymentStatus", required = false) String paymentStatus,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            Model model) {

        List<AdminOrderListDto> orders = service.getAllOrders(customerName,orderStatus, paymentStatus,startDate,endDate);
        
        model.addAttribute("orders", orders);
        model.addAttribute("selectedCustomerName", customerName);
        model.addAttribute("selectedOrderStatus", orderStatus);
        model.addAttribute("selectedPaymentStatus", paymentStatus);
        model.addAttribute("selectedStartDate", startDate);
        model.addAttribute("selectedEndDate", endDate);
        
        return "admin/orders/order-list";
    }

    @PostMapping("/detail")
    public String showOrderDetailPage(@RequestParam("orderId") String orderId, Model model) {
        AdminOrderDetailDto orderDetail = service.getOrderDetail(orderId);

        model.addAttribute("order", orderDetail);
        model.addAttribute("allOrderStatuses", OrderStatus.values());
        model.addAttribute("allPaymentStatuses", PaymentStatus.values());
        model.addAttribute("updateDto", new AdminPaymentUpdateDto());

        return "admin/orders/order-detail";
    }

    @PostMapping("/update-status")
	public String updateOrderStatus(@ModelAttribute("updateDto") AdminPaymentUpdateDto updateDto,
									RedirectAttributes redirectAttributes) {
		
		List<String> lowStockWarnings = service.updatePaymentAndOrderStatus(updateDto);

		
		redirectAttributes.addFlashAttribute("successMessage", "Order Status Success");

		
		if (!lowStockWarnings.isEmpty()) {
			String warningMsg = "Low Stock - " 
								+ String.join(", ", lowStockWarnings);
			redirectAttributes.addFlashAttribute("warningMessage", warningMsg);
		}

		return "redirect:/admin/orders";
	}
  
}