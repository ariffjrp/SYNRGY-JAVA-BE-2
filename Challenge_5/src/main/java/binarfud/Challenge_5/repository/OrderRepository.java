package binarfud.Challenge_5.repository;

import binarfud.Challenge_5.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT SUM(o.totalprice) FROM Order o WHERE o.merchant.id = :merchantId AND o.createdDate BETWEEN :startDate AND :endDate AND o.completed = true")
    Double calculateMerchantProfit(@Param("merchantId") UUID merchantId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Order> findByMerchantIdAndCreatedDateBetween(UUID merchantId, Date startDate, Date endDate);
}
