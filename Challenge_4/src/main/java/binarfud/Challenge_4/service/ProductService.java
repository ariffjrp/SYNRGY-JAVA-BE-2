package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Product;
import binarfud.Challenge_4.model.ProductDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAll(long qty);

    Product createProduct(ProductDTO productDTO, UUID merchantId);

    Product updateUpdate(UUID updateId, Product updatedProduct);

    void deleteUpdate(UUID updateId);
}