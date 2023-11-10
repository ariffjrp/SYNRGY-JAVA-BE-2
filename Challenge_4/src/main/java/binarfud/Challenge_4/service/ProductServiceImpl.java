package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Merchant;
import binarfud.Challenge_4.model.Product;
import binarfud.Challenge_4.model.ProductDTO;
import binarfud.Challenge_4.repository.MerchantRepository;
import binarfud.Challenge_4.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public List<Product> getAll(long qty) {
        return productRepository.findByQtyGreaterThan(qty);
    }

    @Override
    public List<Product> filterPrice(long maxPrice, long minPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public Product createProduct(ProductDTO productDTO, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(() -> new EntityNotFoundException("Merchant not found"));

        Product product = new Product()
                .setName(productDTO.getName())
                .setDeskripsi(productDTO.getDeskripsi())
                .setPrice(productDTO.getPrice())
                .setQty(productDTO.getQty())
                .setMerchant(merchant);
        logger.info(String.valueOf(product));
        return productRepository.save(product);
    }

    @Override
    public Product updateUpdate(UUID updateId, ProductDTO updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(updateId);
        if (optionalProduct.isPresent()) {
            Product productToUpdate = optionalProduct.get();
            productToUpdate.setName(updatedProduct.getName());
            productToUpdate.setDeskripsi(updatedProduct.getDeskripsi());
            productToUpdate.setQty(updatedProduct.getQty());
            productToUpdate.setPrice(updatedProduct.getPrice());
            logger.info(String.valueOf(productToUpdate));
            return productRepository.save(productToUpdate);
        } else {
            logger.error(null);
            return null;
        }
    }

    @Override
    public void deleteUpdate(UUID updateId) {
        productRepository.deleteById(updateId);
        logger.info("Product Remove Merchant");
    }
}
