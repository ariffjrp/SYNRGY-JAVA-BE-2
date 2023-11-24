package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.model.Order;
import binarfud.Challenge_5.model.dto.OrderDTO;
import binarfud.Challenge_5.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderDTO orderDTO, @RequestParam("user_id") UUID userId, @RequestParam("merchant_id")  UUID merchantId,
                                                           Authentication authentication, Principal principal) {
        Map<String , Object> response = new HashMap<>();
        Order createdOrder = orderService.checkout(orderDTO, userId, merchantId);
        if (createdOrder != null) {
            logger.info(String.valueOf(createdOrder));
            response.put("Message", "Order was created successfully");
            response.put("Data", createdOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("Message", "Failed to create order. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Object> updateCompleted(@PathVariable UUID orderId, Authentication authentication, Principal principal) {
        try {
            Order updatedOrder = orderService.UpdateCompleted(orderId);
            if (updatedOrder != null) {
                return ResponseEntity.ok("Order marked as completed.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
