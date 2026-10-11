package ai.shoppingapp.controller;


import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ai.shoppingapp.model.OrderHistoryDto;
import ai.shoppingapp.model.OrderDetailsDto;
import ai.shoppingapp.model.OrderSummaryDto;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.service.OrderHistoryService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class OrderHistoryController {

    private final OrderHistoryService orderService;

    public OrderHistoryController(OrderHistoryService orderService) {
        this.orderService = orderService;
    }
    
    private String checkCustomerAccess(HttpSession session) {

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		
		if ("ADMIN".equalsIgnoreCase(String.valueOf(loggedInUser.getRole())) || "SUPER_ADMIN".equalsIgnoreCase(String.valueOf(loggedInUser.getRole()))) {

			return "redirect:/admin/dashboard";
		}

		return null;
	}
    
    @GetMapping("/orders")
    public String showOrderHistory(HttpSession session, Model model) {
    	
    	String accessResult = checkCustomerAccess(session);
        if (accessResult != null) {
            return accessResult;
        }
    	
    	UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        String userId = user.getId();
        List<OrderHistoryDto> orders = orderService.getOrderHistory(userId);
        model.addAttribute("orders", orders);
        return "user/order_history";
    }
    
    @PostMapping("/details")
    public String showOrderDetails(@RequestParam("orderId") String orderId, HttpSession session, Model model) {
    	
    	String accessResult = checkCustomerAccess(session);
        if (accessResult != null) {
            return accessResult;
        }
    	
    	UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        String userId = user.getId();
        List<OrderDetailsDto> items = orderService.getOrderDetails(userId, orderId);
        OrderSummaryDto summary = orderService.getOrderSummary(userId, orderId);

        model.addAttribute("items", items);
        model.addAttribute("summary", summary);
        return "user/order_details";
    }
    //...
}
