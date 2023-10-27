package binarfud.Challenge_4.controller;

import binarfud.Challenge_4.model.OrderDetail;
import binarfud.Challenge_4.model.OrderDetailDTO;
import binarfud.Challenge_4.service.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    private final static Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(
            @RequestBody OrderDetailDTO orderDetailDTO,
            @RequestParam("productId") UUID productId,
            @RequestParam("userId") UUID userId
    ) {
        try {
            OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO, productId, userId);
            logger.info(String.valueOf(createdOrderDetail));
            return ResponseEntity.ok(createdOrderDetail);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailByUserId(@PathVariable UUID userId) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByUserId(userId);
            logger.info(orderDetails.toString());
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
