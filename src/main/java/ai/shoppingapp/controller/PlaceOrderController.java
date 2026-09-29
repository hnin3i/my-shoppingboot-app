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
import org.springframework.web.bind.annotation.RestController;

import ai.shoppingapp.model.PlaceOrderRequestDto;
import ai.shoppingapp.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class PlaceOrderController {
	
	private final OrderService orderService;
	
	public PlaceOrderController(OrderService orderService) {
		this.orderService=orderService;
	}
	
	@PostMapping("/place")
	public ResponseEntity<Map<String, String>> placeOrder(@RequestBody PlaceOrderRequestDto requestDto)
	{
		String orderNumber=this.orderService.placeOrder(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("orderNumber", orderNumber));
	}
}
