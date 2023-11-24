package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.model.Account;
import binarfud.Challenge_5.model.dto.AccountDTO;
import binarfud.Challenge_5.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAccount(@PathVariable UUID id, @RequestBody AccountDTO accountUpdate,
                                                             Authentication authentication, Principal principal) {
        try {
            Map<String, Object> response = new HashMap<>();
            Account updatedAccount = accountService.update(id, accountUpdate);

            if (updatedAccount != null) {
                logger.info(String.valueOf(updatedAccount));
                response.put("Message", "Account successfully updated.");
                response.put("Data", updatedAccount);
                return ResponseEntity.ok(response);
            } else {
                logger.warn(String.valueOf(updatedAccount));
                response.put("Message", "Account not found!!!");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to update account. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
