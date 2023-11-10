package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.*;
import binarfud.Challenge_5.repository.MerchantRepository;
import binarfud.Challenge_5.repository.OrderDetailRepository;
import binarfud.Challenge_5.repository.ProductRepository;
import binarfud.Challenge_5.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantRepository merchantRepository;

    private final static Logger logger = LoggerFactory.getLogger(OrderDetailService.class);

    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO, UUID productId, UUID userId, UUID merchantId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Users user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(() -> new EntityNotFoundException("Merchant not found"));

        if (!product.getMerchant().getId().equals(merchantId)) {
            throw new IllegalArgumentException("Product does not belong to the specified merchant");
        }

        Integer priceItem = Math.toIntExact(product.getPrice());
        OrderDetail orderDetail = new OrderDetail()
                .setQuantity(orderDetailDTO.getQuantity())
                .setPriceItem(priceItem)
                .setCatatan(orderDetailDTO.getCatatan())
                .setProduct(product)
                .setUser(user)
                .setMerchant(merchant);

        return orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> getOrderDetailByUserId(UUID userId) {
        return orderDetailRepository.findByUser_Id(userId);
    }
}
