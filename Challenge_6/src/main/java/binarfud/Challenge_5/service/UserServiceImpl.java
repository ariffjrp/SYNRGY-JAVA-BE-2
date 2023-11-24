package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.Account;
import binarfud.Challenge_5.model.ERole;
import binarfud.Challenge_5.model.Role;
import binarfud.Challenge_5.model.Users;
import binarfud.Challenge_5.model.dto.UsersDTO;
import binarfud.Challenge_5.repository.AccountRepository;
import binarfud.Challenge_5.repository.RoleRepository;
import binarfud.Challenge_5.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Users getUserById(UUID usersid){
        Optional<Users> users = userRepository.findById(usersid);
        if (users.isPresent()){
            return users.get();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UsersDTO createUser(UsersDTO usersDTO) {
        Users users = new Users();
        users.setUsername(usersDTO.getUsername());
        users.setEmailAddress(usersDTO.getEmailAddress());

        String hashedPassword = passwordEncoder.encode(usersDTO.getPassword());
        users.setPassword(hashedPassword);

        Set<ERole> selectedRoles = usersDTO.getRole();

        Set<Role> existingRoles = new HashSet<>();

        for (ERole selectedRole : selectedRoles) {
            Role existingRole = roleRepository.findByRole(selectedRole);

            if (existingRole == null) {
                Role newRole = new Role(selectedRole);
                roleRepository.save(newRole);
                existingRoles.add(newRole);
            } else {
                existingRoles.add(existingRole);
            }
        }

        users.setRoles(existingRoles);

        Account account = new Account();

        users.setAccount(account);
        account.setSaldo(0);
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

    @Override
    public String findEmailByUsername(String username)
    {
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getEmailAddress();
        }
        return null;
    }
}
