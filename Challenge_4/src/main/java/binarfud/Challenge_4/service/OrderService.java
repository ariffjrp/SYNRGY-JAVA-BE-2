package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Order;
import binarfud.Challenge_4.model.OrderDTO;
import binarfud.Challenge_4.model.OrderDetail;
import binarfud.Challenge_4.model.Users;
import binarfud.Challenge_4.repository.OrderDetailRepository;
import binarfud.Challenge_4.repository.OrderRepository;
import binarfud.Challenge_4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    UserRepository userRepository;

    public Order checkout(OrderDTO orderDTO, UUID userId) {
        List<OrderDetail> cartItems = orderDetailRepository.findByUser_Id(userId);

        if (!cartItems.isEmpty()) {
            Integer total = 0;

            for (OrderDetail orderDetail : cartItems) {
                total += orderDetail.getQuantity() * orderDetail.getPriceItem();
            }

            Integer totalQuantity = cartItems.stream()
                    .mapToInt(OrderDetail::getQuantity)
                    .sum();

            Order order = new Order();
            Users user = userRepository.findById(userId).orElse(null);
            order.setUser(user);
            order.setTotalprice(total);
            order.setDestinationAddress(orderDTO.getDestinationAddress());
            order.setTotalqty(totalQuantity);
            order.setCompleted(false);
            order.setOrderTime(LocalDateTime.now());
            orderRepository.save(order);
            return order;
        }
        return null;
    }
}
