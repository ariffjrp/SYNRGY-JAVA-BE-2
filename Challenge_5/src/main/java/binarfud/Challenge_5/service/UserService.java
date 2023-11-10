package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.Users;
import binarfud.Challenge_5.model.UsersDTO;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface UserService {

    @Transactional
    void callCreateUserAccount(String username, String emailAddress, String password);

    UsersDTO createUser(UsersDTO usersDTO);

    Users updateUser1(UUID userId, UsersDTO updatedUser);

    Users getUserById(UUID usersid);

    void deleteUser(UUID userId);

    Users updateUser(UUID id, String username, String emailAddress, String password);
}
