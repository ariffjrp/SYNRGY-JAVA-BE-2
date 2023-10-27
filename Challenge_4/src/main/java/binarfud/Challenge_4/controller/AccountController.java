package binarfud.Challenge_4.controller;

import binarfud.Challenge_4.model.Account;
import binarfud.Challenge_4.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PatchMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID id, @RequestBody Account accountUpdate) {
        try {
            Account updatedAccount = accountService.update(id, accountUpdate);

            if (updatedAccount != null) {
                logger.info(String.valueOf(updatedAccount));
                return ResponseEntity.ok(updatedAccount);
            } else {
                logger.warn(String.valueOf(updatedAccount));
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
