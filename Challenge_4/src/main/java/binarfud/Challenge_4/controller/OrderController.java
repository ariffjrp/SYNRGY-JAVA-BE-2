package binarfud.Challenge_4.controller;

import binarfud.Challenge_4.model.Order;
import binarfud.Challenge_4.model.OrderDTO;
import binarfud.Challenge_4.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO, @RequestParam("user_id") UUID userId) {
        Order createdOrder = orderService.checkout(orderDTO, userId);

        if (createdOrder != null) {
            logger.info(String.valueOf(createdOrder));
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
