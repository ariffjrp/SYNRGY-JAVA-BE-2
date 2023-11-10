package binarfud.Challenge_4.repository;

import binarfud.Challenge_4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByQtyGreaterThan(long qty);

    List<Product> findByPriceBetween(long maxPrice, long minPrice);
}
