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
import ai.shoppingapp.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class PlaceOrderController {
	
	private final OrderService orderService;
	
	public PlaceOrderController(OrderService orderService) {
		this.orderService=orderService;
	}
	
	@PostMapping(value="/place",consumes="multipart/form-data")
	public ResponseEntity<Map<String, String>> placeOrder(@RequestPart("order") PlaceOrderRequestDto requestDto,
			@RequestPart(value="paymentProof",required=false) MultipartFile paymentProof)
	{
		String orderNumber=this.orderService.placeOrder(requestDto,paymentProof);
		return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("orderNumber", orderNumber));
	}
}
