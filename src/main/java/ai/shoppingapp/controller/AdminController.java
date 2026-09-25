package ai.shoppingapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.service.OrderHistoryService;
import ai.shoppingapp.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final OrderHistoryService orderHistoryService;

    public AdminController(UserService userService, OrderHistoryService orderHistoryService) {
        this.userService = userService;
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

//    @GetMapping
//    public String listUsers(@RequestParam(value = "keyword", required = false) String keyword,
//                            @RequestParam(value = "role", required = false) String role,
//                            Model model) {
//        
//        List users = userService.searchUsersWithFilter(keyword, role);
//        
//        model.addAttribute("users", users);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("selectedRole", role);
//        return "admin/users/list"; // templates/admin/users/list.html
//    }
//
//    // Endpoint called by JavaScript when clicking "View Orders"
//    @GetMapping("/orders")
//    @ResponseBody
//    public List getUserOrders(@RequestParam("userId") String userId) {
//        return orderHistoryService.getOrderHistory(userId);
//    }
    @GetMapping
    public String listUsers(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "role", required = false) String role,
                            Model model) {

        List users = userService.searchUsersWithFilter(keyword, role);

        // Map each user ID to their list of OrderHistoryDto / OrderSummaryDto
        Map<> userOrdersMap = new HashMap<>();
        for (UserModel user : users) {
            userOrdersMap.put(String.valueOf(user.getId()), orderHistoryService.getOrdersByUserId(String.valueOf(user.getId())));
        }

        model.addAttribute("users", users);
        model.addAttribute("userOrdersMap", userOrdersMap);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedRole", role);

        return "admin/users/list";
    }

  
}