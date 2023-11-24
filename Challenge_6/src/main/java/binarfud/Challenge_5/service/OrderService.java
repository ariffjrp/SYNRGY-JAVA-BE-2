package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.*;
import binarfud.Challenge_5.model.dto.OrderDTO;
import binarfud.Challenge_5.repository.MerchantRepository;
import binarfud.Challenge_5.repository.OrderDetailRepository;
import binarfud.Challenge_5.repository.OrderRepository;
import binarfud.Challenge_5.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantRepository merchantRepository;


    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }


    public Order checkout(OrderDTO orderDTO, UUID userId, UUID merchantId) {
        Users user = userRepository.findById(userId).orElse(null);
        List<OrderDetail> cartItems = orderDetailRepository.findByUser_Id(userId);

        if (user != null && !cartItems.isEmpty()) {
            if (cartItems.stream().anyMatch(orderDetail -> !orderDetail.getMerchant().getId().equals(merchantId))) {
                throw new IllegalArgumentException("All cart items must belong to the same merchant.");
            }

            Integer total = 0;
            for (OrderDetail orderDetail : cartItems) {
                total += orderDetail.getQuantity() * orderDetail.getPriceItem();
            }

            Integer totalQuantity = cartItems.stream()
                    .mapToInt(OrderDetail::getQuantity)
                    .sum();

            Order order = new Order();
            order.setUser(user);
            order.setMerchant(merchantRepository.findById(merchantId)
                    .orElseThrow(() -> new EntityNotFoundException("Merchant not found")));
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


    public Order UpdateCompleted(UUID id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            Order updateOrder = order.get();
            updateOrder.setCompleted(true);

            if (updateOrder.isCompleted()) {
                Merchant merchant = updateOrder.getMerchant();

                if (merchant != null) {
                    long totalIncome = merchant.getIncome() + updateOrder.getTotalprice();
                    merchant.setIncome(totalIncome);

                    merchantRepository.save(merchant);

                    List<OrderDetail> orderDetails = orderDetailRepository.findByMerchant_id(merchant.getId());

                    orderDetailRepository.deleteAll(orderDetails);
                }
            }

            return orderRepository.save(updateOrder);
        } else {
            return null;
        }
    }
}
