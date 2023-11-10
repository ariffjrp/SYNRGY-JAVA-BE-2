package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.Product;
import binarfud.Challenge_5.model.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAll(long qty);

    List<Product> filterPrice(long maxPrice, long minPrice);

    Product createProduct(ProductDTO productDTO, UUID merchantId);

    Product updateUpdate(UUID updateId, ProductDTO updatedProduct);

    void deleteUpdate(UUID updateId);
}
