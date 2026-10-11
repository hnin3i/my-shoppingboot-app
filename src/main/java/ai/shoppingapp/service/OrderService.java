
package ai.shoppingapp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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

    public OrderService(
            OrderItemRepository orderItemRepo,
            OrderRepository orderRepo,
            StockRepository stockRepo,
            OrderProductRepository orderProductRepo,
            PaymentProofStorageService paymentProofStorageService) {

        this.orderItemRepo = orderItemRepo;
        this.orderRepo = orderRepo;
        this.stockRepo = stockRepo;
        this.orderProductRepo = orderProductRepo;
        this.paymentProofStorageService = paymentProofStorageService;
    }

    @Transactional
    public String placeOrder(
            String userId,
            PlaceOrderRequestDto requestDto,
            MultipartFile paymentProof) {

        String paymentProofPath = null;

        try {

            String orderId = UUID.randomUUID().toString();
            String orderNumber = "ORD-" + System.currentTimeMillis();
            LocalDateTime now = LocalDateTime.now();

            /*
             * 1. Validate order items
             */
            validateOrderItems(requestDto);

            /*
             * 2. Calculate discounted subtotal
             *
             * Product price and discount are queried
             * from database inside this function.
             */
            BigDecimal subtotalAmount =calculateSubtotal(requestDto);

            /*
             * 3. Calculate tax based on discounted subtotal
             */
            BigDecimal taxAmount =calculateTax(subtotalAmount);

            /*
             * 4. Calculate shipping fee
             */
            BigDecimal shippingFee =calculateShippingFee(requestDto);

            /*
             * 5. Calculate final grand total
             *
             * discounted subtotal
             * + tax
             * + shipping
             */
            BigDecimal grandTotal =calculateGrandTotal(
                            subtotalAmount,
                            taxAmount,
                            shippingFee);

            /*
             * 6. Save payment proof
             */
            if (!"COD".equals(requestDto.getPaymentMethod())) {

                if (paymentProof == null || paymentProof.isEmpty()) {
                    throw new RuntimeException(
                            "Payment proof is required.");
                }

                paymentProofPath =savePaymentProof(paymentProof);
            }

            /*
             * 7. Create Order Entity
             *
             * total_amount will contain
             * the discounted final amount.
             */
            OrderEntity order = toOrderEntity(
                    requestDto,
                    userId,
                    orderId,
                    orderNumber,
                    subtotalAmount,
                    taxAmount,
                    shippingFee,
                    grandTotal,
                    paymentProofPath,
                    now
            );

            /*
             * 8. Save order
             */
            this.orderRepo.save(order);

            /*
             * 9. Save order items
             *
             * Each item price/subtotal also uses
             * discounted price.
             */
            saveOrderItems(
                    orderId,
                    requestDto,
                    now);

            return orderNumber;

        } catch (Exception e) {

            /*
             * Database transaction will rollback.
             *
             * Payment proof file is not inside DB transaction,
             * so manually delete it if something fails.
             */
            if (paymentProofPath != null) {
                paymentProofStorageService.delete(paymentProofPath);
            }

            throw e;
        }
    }


    /*
     * =========================================================
     * 1. VALIDATE ORDER ITEMS
     * =========================================================
     */
    private void validateOrderItems(
            PlaceOrderRequestDto requestDto) {

        if (requestDto.getItems() == null
                || requestDto.getItems().isEmpty()) {

            throw new RuntimeException(
                    "Order items cannot be empty.");
        }

        for (OrderItemRequestDto itemDto
                : requestDto.getItems()) {

            if (itemDto.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Invalid quantity.");
            }

            /*
             * Query stock from database.
             */
            Stock stock =stockRepo.findById(itemDto.getStockId());

            if (stock == null) {

                throw new RuntimeException(
                        "Stock not found.");
            }

            /*
             * Re-check current stock.
             */
            if (stock.getStock_qty()< itemDto.getQuantity()) {

                throw new RuntimeException(
                        "Not enough stock.");
            }

            /*
             * Query product from database.
             */
            Product product =orderProductRepo.findById(stock.getProduct_id());

            if (product == null) {

                throw new RuntimeException(
                        "Product not found.");
            }
        }
    }


    /*
     * =========================================================
     * 2. CALCULATE SUBTOTAL
     * =========================================================
     */
    private BigDecimal calculateSubtotal(
            PlaceOrderRequestDto requestDto) {

        BigDecimal subtotalAmount =BigDecimal.ZERO;

        for (OrderItemRequestDto itemDto: requestDto.getItems()) {

            /*
             * Query stock again.
             */
            Stock stock =stockRepo.findById(itemDto.getStockId());

            if (stock == null) {

                throw new RuntimeException(
                        "Stock not found.");
            }

            /*
             * Query product again.
             */
            Product product =orderProductRepo.findById(stock.getProduct_id());

            if (product == null) {

                throw new RuntimeException(
                        "Product not found.");
            }

            /*
             * Calculate discounted price.
             */
            BigDecimal finalPrice =calculateDiscount(product);

            BigDecimal quantity =BigDecimal.valueOf( itemDto.getQuantity());

            BigDecimal itemSubtotal =finalPrice.multiply(quantity);

            subtotalAmount =subtotalAmount.add(itemSubtotal);
        }

        return subtotalAmount;
    }


    /*
     * =========================================================
     * 3. DISCOUNT CALCULATION
     * =========================================================
     *
    
     */
    private BigDecimal calculateDiscount(
            Product product) {

        BigDecimal price =product.getPrice();

        if (price == null) {

            throw new RuntimeException(
                    "Product price is missing.");
        }

        /*
         * No discount.
         */
        if (!product.isIs_discount()) {

            return price;
        }

        BigDecimal discountAmount =product.getDiscount_price();

        /*
         * Discount value is missing.
         * Treat it as no discount.
         */
        if (discountAmount == null || discountAmount.compareTo(BigDecimal.ZERO) <= 0) {

            return price;
        }

        /*
         * Discount cannot be greater than price.
         */
        if (discountAmount.compareTo(price) > 0) {

            discountAmount = price;
        }

        /*
         * Final discounted price.
         */
        return price.subtract(discountAmount);
    }


    /*
     * =========================================================
     * 4. CALCULATE TAX
     * =========================================================
     *
     * Tax is calculated from the DISCOUNTED subtotal.
     */
    private BigDecimal calculateTax( BigDecimal subtotalAmount) {

        BigDecimal taxRate = BigDecimal.valueOf(5);

        return subtotalAmount
                .multiply(taxRate)
                .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
    }


    /*
     * =========================================================
     * 5. CALCULATE SHIPPING FEE
     * =========================================================
     */
    private BigDecimal calculateShippingFee(
            PlaceOrderRequestDto requestDto) {

        /*
         * Current fixed shipping fee.
         */
        return BigDecimal.valueOf(5000);
    }


    /*
     * =========================================================
     * 6. CALCULATE GRAND TOTAL
     * =========================================================
     */
    private BigDecimal calculateGrandTotal(
            BigDecimal subtotalAmount,
            BigDecimal taxAmount,
            BigDecimal shippingFee) {

        return subtotalAmount
                .add(taxAmount)
                .add(shippingFee);
    }


    /*
     * =========================================================
     * 7. SAVE PAYMENT PROOF
     * =========================================================
     */
    private String savePaymentProof(
            MultipartFile paymentProof) {

        return paymentProofStorageService.save(paymentProof);
    }


    /*
     * =========================================================
     * 8. SAVE ORDER ITEMS
     * =========================================================
     */
    private void saveOrderItems(
            String orderId,
            PlaceOrderRequestDto requestDto,
            LocalDateTime now) {

        for (OrderItemRequestDto itemDto: requestDto.getItems()) {

           
            Stock stock =stockRepo.findById(itemDto.getStockId());

            if (stock == null) {

                throw new RuntimeException(
                        "Stock not found.");
            }

            /*
             * Query product again.
             */
            Product product =orderProductRepo.findById(stock.getProduct_id());

            if (product == null) {

                throw new RuntimeException(
                        "Product not found.");
            }

            /*
             * Get discounted price.
             */
            BigDecimal finalPrice =calculateDiscount(product);

            BigDecimal quantity = BigDecimal.valueOf(itemDto.getQuantity());

            BigDecimal itemSubtotal =finalPrice.multiply(quantity);

            OrderItemEntity entity =
                    toOrderItemEntity(
                            orderId,
                            itemDto,
                            finalPrice,
                            itemSubtotal,
                            now);

            orderItemRepo.save(entity);
        }
    }


    /*
     * =========================================================
     * 9. CONVERT TO ORDER ITEM ENTITY
     * =========================================================
     */
    private OrderItemEntity toOrderItemEntity(
            String orderId,
            OrderItemRequestDto dto,
            BigDecimal price,
            BigDecimal subtotal,
            LocalDateTime now) {

        OrderItemEntity entity =new OrderItemEntity();

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


    /*
     * =========================================================
     * 10. CONVERT TO ORDER ENTITY
     * =========================================================
     */
    private OrderEntity toOrderEntity(
            PlaceOrderRequestDto requestDto,
            String userId,
            String orderId,
            String orderNumber,
            BigDecimal subtotalAmount,
            BigDecimal taxAmount,
            BigDecimal shippingFee,
            BigDecimal totalAmount,
            String paymentProof,
            LocalDateTime now) {

        OrderEntity entity =new OrderEntity();
        
        entity.setId(orderId);
        entity.setUser_id(userId);
        entity.setOrder_number(orderNumber);
        entity.setSubtotal_amount(subtotalAmount);
        entity.setTax_amount(taxAmount);
        entity.setShipping_fee( shippingFee);
        entity.setTotal_amount(totalAmount);
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