package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Account;
import binarfud.Challenge_4.model.Users;
import binarfud.Challenge_4.model.UsersDTO;
import binarfud.Challenge_4.repository.AccountRepository;
import binarfud.Challenge_4.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    AccountRepository accountRepository;

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional
    public UsersDTO createUser(UsersDTO usersDTO) {
        Users users = new Users();
        users.setUsername(usersDTO.getUsername());
        users.setEmailAddress(usersDTO.getEmailAddress());
        users.setPassword(usersDTO.getPassword());

        Account account = new Account();

        users.setAccount(account);
        account.setUser(users);

        userRepository.save(users);
        accountRepository.save(account);
        logger.info(String.valueOf(usersDTO));
        return usersDTO;
    }

    @Override
    @Transactional
    public void callCreateUserAccount(String username, String emailAddress, String password) {
        userRepository.createUserAccount(username, emailAddress, password);
    }


    @Override
    public Users updateUser1(UUID userId, UsersDTO updatedUser) {
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Users userToUpdate = optionalUser.get();
            userToUpdate.setEmailAddress(updatedUser.getEmailAddress());
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setPassword(updatedUser.getPassword());
            logger.info(String.valueOf(userToUpdate));
            return userRepository.save(userToUpdate);
        } else {
            logger.error(null);
            return null;
        }
    }

    public Users updateUser(UUID userId, String username, String emailAddress, String password) {
        userRepository.updateUser(userId, username, emailAddress, password);
        return null;
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
        logger.info("User Remove Account");
    }
}
