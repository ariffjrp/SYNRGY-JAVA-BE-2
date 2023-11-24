package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.model.Merchant;
import binarfud.Challenge_5.model.Order;
import binarfud.Challenge_5.service.MerchantService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@Component
@RequestMapping("/api/v1/merchants")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    private final static Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "a", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "b", content = {
                    @Content(mediaType = "application/json")
            })
    })
    public ResponseEntity<Map<String, Object>> getOpenMerchants(Authentication authentication, Principal principal) {
        try {
            List<Merchant> optionalMerchant = merchantService.getOpenMerchants();
            Map<String, Object> response = new HashMap<>();

            if (optionalMerchant == null || optionalMerchant.isEmpty()) {
                logger.warn("Message : Merchant Open Not Found");
                response.put("Message", "Merchant Open Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            logger.info("List Merchant");
            response.put("Message", "Merchant Open was retrieved successfully.");
            response.put("Data", optionalMerchant);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to Find Data Merchant Open. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/income/{id}")
    public ResponseEntity<Map<String, Object>> getIncomeMerchantId(@PathVariable UUID id, Authentication authentication, Principal principal){
        try {
            Merchant merchant = merchantService.getMerchantById(id);
            Map<String, Object> response = new HashMap<>();

            response.put("Message", "Merchant By Id was retrieved successfully");
            response.put("Income", merchant.getIncome());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Failed to Find Data Merchant. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMerchantId(@PathVariable UUID id, Authentication authentication, Principal principal){
        try {
            Merchant merchant = merchantService.getMerchantById(id);
            Map<String, Object> response = new HashMap<>();

            response.put("Message", "Merchant By Id was retrieved successfully");
            response.put("Data", merchant);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Failed to Find Data Merchant. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMerchant(@RequestBody Merchant merchant, Authentication authentication, Principal principal) {
        try {
            Merchant newMerchant = merchantService.create(merchant);
            Map<String, Object> response = new HashMap<>();
            logger.info(String.valueOf(newMerchant));
            response.put("Message", "Merchant was created successfully");
            response.put("Data", newMerchant);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to create Merchant. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> updateMerchantOpenStatus(@PathVariable UUID id, @RequestBody Map<String, Boolean> requestBody,
                                                                        Authentication authentication, Principal principal) {
        try {
            Boolean open = requestBody.get("open");
            Map<String, Object> response = new HashMap<>();
            if (open != null) {
                Merchant updatedMerchant = merchantService.update(id, open);
                if (updatedMerchant != null) {
                    response.put("Message", "Merchant successfully updated.");
                    response.put("Data", updatedMerchant);
                    logger.info(String.valueOf(updatedMerchant));
                    return ResponseEntity.ok(response);
                } else {
                    response.put("Message", "Merchant Id not found!");
                    logger.warn(String.valueOf(updatedMerchant));
                    return ResponseEntity.notFound().build();
                }
            } else {
                response.put("Message", "Bad request");
                logger.error("Bad request");
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e ){
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to create merchant. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMerchant(@PathVariable UUID id, Authentication authentication, Principal principal){
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Merchant successfully deleted.");
            logger.info("Merchant successfully deleted.");
            merchantService.deleteMerchant(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to delete merchant. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/profit/{merchantId}")
    public ResponseEntity<Map<String, Object>> getMerchantProfit(@PathVariable UUID merchantId,
                                                                 @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                 @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                 Authentication authentication, Principal principal) {
        Double profit = merchantService.calculateMerchantProfit(merchantId, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("merchantId", merchantId);
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        response.put("profit", profit);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{merchantId}")
    public List<Order> getMerchantOrders(@PathVariable UUID merchantId,
                                         @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         Authentication authentication, Principal principal) {
        return merchantService.getOrdersByMerchantAndDateRange(merchantId, startDate, endDate);
    }
}
