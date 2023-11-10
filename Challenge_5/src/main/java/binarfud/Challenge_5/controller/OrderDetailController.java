package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.model.OrderDetail;
import binarfud.Challenge_5.model.OrderDetailDTO;
import binarfud.Challenge_5.service.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    private final static Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrderDetail(
            @RequestBody OrderDetailDTO orderDetailDTO,
            @RequestParam("productId") UUID productId,
            @RequestParam("userId") UUID userId,
            @RequestParam("merchantId") UUID merchantId
    ) {
        try {
            Map<String, Object> response = new HashMap<>();
            OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO, productId, userId, merchantId);
            logger.info(String.valueOf(createdOrderDetail));
            response.put("Message", "Order Detail was created successfully");
            response.put("Data", createdOrderDetail);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to create order detail. Please check application log.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getOrderDetailByUserId(@PathVariable UUID userId) {
        try {
            Map<String, Object> response = new HashMap<>();
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByUserId(userId);
            logger.info(orderDetails.toString());
            response.put("Message", "Order Detail was retrieved successfully.");
            response.put("Data", orderDetails);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to Find Data Order detail. Please check application log.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
