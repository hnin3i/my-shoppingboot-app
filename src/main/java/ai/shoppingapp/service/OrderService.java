package ai.shoppingapp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ai.shoppingapp.model.OrderItemRequestDto;
import ai.shoppingapp.model.PlaceOrderRequestDto;
import ai.shoppingapp.repository.OrderItemRepository;
import ai.shoppingapp.repository.OrderProductRepository;
import ai.shoppingapp.repository.OrderRepository;
import ai.shoppingapp.repository.StockRepository;
import ai.shoppingapp.repository.entity.OrderEntity;
import ai.shoppingapp.repository.entity.OrderItemEntity;
import ai.shoppingapp.repository.entity.Product;
import ai.shoppingapp.repository.entity.Stock;

@Service
public class OrderService {

    private final OrderItemRepository orderItemRepo;
    private final OrderRepository orderRepo;
    private final StockRepository stockRepo;
    private final OrderProductRepository orderProductRepo;
    private final PaymentProofStorageService paymentProofStorageService;

    public OrderService(OrderItemRepository orderItemRepo,
                        OrderRepository orderRepo,
                        StockRepository stockRepo,
                        OrderProductRepository orderProductRepo,
                        PaymentProofStorageService paymentProofStorageService) {
        this.orderItemRepo = orderItemRepo;
        this.orderRepo = orderRepo;
        this.stockRepo = stockRepo;
        this.orderProductRepo = orderProductRepo;
        this.paymentProofStorageService=paymentProofStorageService;
    }

    @Transactional
    public String placeOrder(PlaceOrderRequestDto requestDto,MultipartFile paymentProof) {
        String orderId = UUID.randomUUID().toString();
        String orderNumber = "ORD-" + System.currentTimeMillis();
        LocalDateTime now = LocalDateTime.now();
        BigDecimal subtotalAmount = BigDecimal.ZERO;

        List<OrderItemEntity> orderItemToSave = new ArrayList<>();

        
        for (OrderItemRequestDto itemDto : requestDto.getItems()) {
            
            if (itemDto.getQuantity() <= 0) {
                throw new RuntimeException("Invalid quantity.");
            }

            Stock stock =this.stockRepo.findById(itemDto.getStockId());
            if (stock == null) {
                throw new RuntimeException("Stock not found.");
            }

            if (stock.getStock_qty() < itemDto.getQuantity()) {
                throw new RuntimeException("Not enough stock.");
            }

            Product product = this.orderProductRepo.findById(stock.getProduct_id());
            if (product == null) {
                throw new RuntimeException("Product not found.");
            }

            BigDecimal price = product.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(itemDto.getQuantity());
            BigDecimal itemSubtotal = price.multiply(quantity);

           
            subtotalAmount = subtotalAmount.add(itemSubtotal);
            
           
            
            OrderItemEntity itemEntity = toOrderItemEntity(orderId, itemDto, price, itemSubtotal, now);
            orderItemToSave.add(itemEntity);

            
//            Stock updated = this.stockRepo.findById(itemDto.getStockId());
//            if (updated == 0) {
//                throw new RuntimeException("Not enough stock.");
//            }
        }

  
        BigDecimal taxRate = BigDecimal.valueOf(5);
        BigDecimal shippingFee = BigDecimal.valueOf(5000);

        
        BigDecimal taxAmount = subtotalAmount.multiply(taxRate)
                                             .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal grandTotal = subtotalAmount.add(shippingFee).add(taxAmount);
        
        String paymentProofPath = null;
        
        if(!"COD".equals(requestDto.getPaymentMethod())) {
        	if(paymentProof ==null || paymentProof.isEmpty()) {
        		throw new RuntimeException("Payment proof is required.");
        	}
        	
        	paymentProofPath=this.paymentProofStorageService.save(paymentProof);
        }

       
        OrderEntity order = toOrderEntity(
                requestDto,
                orderId,
                orderNumber,
                subtotalAmount,
                taxAmount,
                shippingFee,
                grandTotal,
            	   paymentProofPath,
                now
        );
        order.setUser_id("382d6828-b8bc-11f1-ac2f-8038fbbba9bc");//Will change later

        this.orderRepo.save(order);
        for (OrderItemEntity item : orderItemToSave) {
            orderItemRepo.save(item);
        }

        return orderNumber;
    }

    private OrderItemEntity toOrderItemEntity(String orderId, OrderItemRequestDto dto, BigDecimal price, BigDecimal subtotal, LocalDateTime now) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setOrder_id(orderId);
        entity.setStock_id(dto.getStockId());
        
        entity.setPrice(price);
        entity.setQuantity(dto.getQuantity());
        entity.setSubtotal(subtotal);
        
        entity.setCreated_at(now);
        entity.setUpdated_at(now);
        return entity;
    }

    private OrderEntity toOrderEntity(
            PlaceOrderRequestDto requestDto,
            String orderId,
            String orderNumber, 
            BigDecimal subtotalAmount,
            BigDecimal tax,
            BigDecimal shippingFee,
            BigDecimal total,
            String paymentProof,
            LocalDateTime now) {
        
        OrderEntity entity = new OrderEntity();
        entity.setId(orderId);
        entity.setUser_id(requestDto.getUserId());
        entity.setOrder_number(orderNumber);
        
        entity.setSubtotal_amount(subtotalAmount);
        entity.setTax_amount(tax);
        entity.setShipping_fee(shippingFee);
        entity.setTotal_amount(total);
        
        entity.setStatus("PENDING");
        entity.setPayment_method(requestDto.getPaymentMethod());
        entity.setPayment_status("UNPAID");
        entity.setShipping_address(requestDto.getShippingAddress());
        entity.setPhone_no(requestDto.getPhoneNo());
        entity.setCreated_at(now);
        entity.setUpdated_at(now);
        entity.setPayment_confirm_photo(paymentProof);
        entity.setAdditional_note(requestDto.getOrderNotes());
        return entity;
    }
}