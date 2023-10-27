package binarfud.Challenge_4.controller;

import binarfud.Challenge_4.model.Merchant;
import binarfud.Challenge_4.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/v1/merchants")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    private final static Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @GetMapping
    public List<Merchant> getAllMerchants() {
        logger.info("List Merchant");
        return merchantService.getOpenMerchants();
    }

    @PostMapping
    public ResponseEntity<Merchant> createMerchant(@RequestBody Merchant merchant) {
        try {
            Merchant newMerchant = merchantService.create(merchant);
            logger.info(String.valueOf(newMerchant));
            return ResponseEntity.status(HttpStatus.CREATED).body(newMerchant);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Merchant> updateMerchantOpenStatus(@PathVariable UUID id, @RequestBody Map<String, Boolean> requestBody) {
        try {
            Boolean open = requestBody.get("open");
            if (open != null) {
                Merchant updatedMerchant = merchantService.update(id, open);
                if (updatedMerchant != null) {
                    logger.info(String.valueOf(updatedMerchant));
                    return ResponseEntity.ok(updatedMerchant);
                } else {
                    logger.warn(String.valueOf(updatedMerchant));
                    return ResponseEntity.notFound().build();
                }
            } else {
                logger.error("Bad request");
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e ){
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
