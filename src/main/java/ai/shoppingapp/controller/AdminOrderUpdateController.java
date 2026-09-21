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
    
 // ၁။ Order စာရင်း အားလုံးနှင့် Filter စစ်ထားသော စာရင်းများ ထုတ်ပြမည့် Page
    @GetMapping
    public String showOrderListPage(
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "paymentStatus", required = false) String paymentStatus,
            Model model) {

        List<AdminOrderListDto> orders = service.getAllOrders(orderStatus, paymentStatus);
        
        model.addAttribute("orders", orders);
        model.addAttribute("selectedOrderStatus", orderStatus);
        model.addAttribute("selectedPaymentStatus", paymentStatus);
        
        return "admin/order-list";
    }

    // ၂။ Order တစ်ခု၏ အသေးစိတ်နှင့် Payment Slip စစ်ဆေးမည့် Page -> http://localhost:8080/admin/orders/{orderId}
    @PostMapping("/detail")
    public String showOrderDetailPage(@RequestParam("orderId") String orderId, Model model) {
        AdminOrderDetailDto orderDetail = service.getOrderDetail(orderId);

        model.addAttribute("order", orderDetail);
        model.addAttribute("allOrderStatuses", OrderStatus.values());
        model.addAttribute("allPaymentStatuses", PaymentStatus.values());
        model.addAttribute("updateDto", new AdminPaymentUpdateDto());

        return "admin/order-detail";
    }

    // ၃။ Status ပြောင်းလဲရန် Submit လုပ်လိုက်လျှင် အလုပ်လုပ်မည့် POST Route
    @PostMapping("/update-status")
    public String updateOrderStatus(@ModelAttribute("updateDto") AdminPaymentUpdateDto updateDto,
                                    RedirectAttributes redirectAttributes) {
        boolean success = service.updatePaymentAndOrderStatus(updateDto);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Success Order Status");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Try Again");
        }
        return "redirect:/admin/orders";
    }
  
}
