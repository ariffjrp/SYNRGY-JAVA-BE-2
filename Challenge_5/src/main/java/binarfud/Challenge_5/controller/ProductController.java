package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.model.Product;
import binarfud.Challenge_5.model.ProductDTO;
import binarfud.Challenge_5.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProduct() {
        try {
            Map<String, Object> response = new HashMap<>();
            List<Product> products = productService.getAll(0);
            logger.info("View Product");
            response.put("Message", "Product was retrieved successfully.");
            response.put("Data", products);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Failed to Find Data Product. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/price")
    public ResponseEntity<Map<String, Object>> getAllProductFilterPrice(
            @RequestParam("maxPrice") long maxPrice,
            @RequestParam("minPrice") long minPrice
    ) {
        try {
            Map<String, Object> response = new HashMap<>();
            List<Product> products = productService.filterPrice(maxPrice, minPrice);
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                response.put("Message", "Product was retrieved successfully.");
                response.put("Data", products);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Failed to Find Data Product. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductDTO productDTO, @RequestParam("merchant_id") UUID merchantId) {
        try {
            Map<String, Object> response = new HashMap<>();
            Product product = productService.createProduct(productDTO, merchantId);
            logger.info(String.valueOf(product));
            response.put("Message", "Product was created successfully");
            response.put("Data", product);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to create Product. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productUpdate) {
        try {
            Map<String, Object> response = new HashMap<>();
            Product updatedProduct = productService.updateUpdate(id, productUpdate);

            if (updatedProduct != null) {
                logger.info(String.valueOf(updatedProduct));
                response.put("Message", "Product was update successfully");
                response.put("Data", updatedProduct);
                return ResponseEntity.ok(response);
            } else {
                logger.warn(String.valueOf(updatedProduct));
                response.put("Message", "Product not found!!!");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to update Product. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable UUID id) {
        try {
            Map<String, Object> response = new HashMap<>();
            productService.deleteUpdate(id);
            logger.info("Product Removed");
            response.put("Message", "Product was delete successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to delete Product. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
