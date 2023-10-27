package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Account;
import binarfud.Challenge_4.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Account update(UUID id, Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account accountToUpdate = optionalAccount.get();
            accountToUpdate.setName(account.getName());
            accountToUpdate.setAddress(account.getAddress());
            accountToUpdate.setPhone(account.getPhone());
            logger.info(String.valueOf(accountToUpdate));
            return accountRepository.save(accountToUpdate);
        } else {
            logger.error(null);
            return null;
        }
    }
}
