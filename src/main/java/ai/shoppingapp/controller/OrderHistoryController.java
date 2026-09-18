package ai.shoppingapp.controller;


import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ai.shoppingapp.model.OrderHistoryDto;
import ai.shoppingapp.model.OrderDetailsDto;
import ai.shoppingapp.model.OrderSummaryDto;
import ai.shoppingapp.service.OrderHistoryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderHistoryController {

    private final OrderHistoryService orderService;

    public OrderHistoryController(OrderHistoryService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/order-history")
    public String showOrderHistory(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");

        if (userId == null) userId = "U002";
        
        List<OrderHistoryDto> orders = orderService.getOrderHistory(userId);
        model.addAttribute("orders", orders);
        return "OrderHistory/order_history";
    }
    
    @PostMapping("/details")
    public String showOrderDetails(@RequestParam("orderId") String orderId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        
        if (userId == null) userId = "U002";
        
        List<OrderDetailsDto> items = orderService.getOrderDetails(userId, orderId);
        OrderSummaryDto summary = orderService.getOrderSummary(userId, orderId);
        
        model.addAttribute("items", items);
        model.addAttribute("summary", summary);
        return "OrderHistory/order_details";
    }
}
