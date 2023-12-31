package binarfud.Challenge_4.repository;

import binarfud.Challenge_4.model.OrderDetail;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findByUser_Id(UUID userId);

    List<OrderDetail> findByMerchant_id(UUID merchantId);
}
