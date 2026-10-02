package ai.shoppingapp.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.shoppingapp.model.PlaceOrderRequestDto;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.service.OrderService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/orders")
public class PlaceOrderController {
	
	private final OrderService orderService;
	
	public PlaceOrderController(OrderService orderService) {
		this.orderService=orderService;
	}
	
	@PostMapping(value="/place",consumes="multipart/form-data")
	public ResponseEntity<Map<String, String>> placeOrder(@RequestPart("order") PlaceOrderRequestDto requestDto,
			@RequestPart(value="paymentProof",required=false) MultipartFile paymentProof,
			HttpSession session)
	{
		UserModel loggedInUser=(UserModel) session.getAttribute("loggedInUser");
		if(loggedInUser==null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(
							Map.of("message",
									"Please login before placing an order"));
		}
		String userId=loggedInUser.getId();
		
		String orderNumber=this.orderService.placeOrder(userId,requestDto,paymentProof);
		return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("orderNumber", orderNumber));
	}
}
