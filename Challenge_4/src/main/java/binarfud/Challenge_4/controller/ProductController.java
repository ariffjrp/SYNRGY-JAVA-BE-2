package binarfud.Challenge_4.controller;

import binarfud.Challenge_4.model.Product;
import binarfud.Challenge_4.model.ProductDTO;
import binarfud.Challenge_4.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public List<Product> getAllProduct() {
        logger.info("View Product");
        return productService.getAll(0);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO, @RequestParam("merchant_id") UUID merchantId) {
        try {
            Product product = productService.createProduct(productDTO, merchantId);
            logger.info(String.valueOf(product));
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product productUpdate) {
        try {
            Product updatedProduct = productService.updateUpdate(id, productUpdate);

            if (updatedProduct != null) {
                logger.info(String.valueOf(updatedProduct));
                return ResponseEntity.ok(updatedProduct);
            } else {
                logger.warn(String.valueOf(updatedProduct));
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteUpdate(id);
            logger.info("Product Removed");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
