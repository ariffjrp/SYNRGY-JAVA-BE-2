package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Users;
import binarfud.Challenge_4.model.UsersDTO;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface UserService {

    @Transactional
    void callCreateUserAccount(String username, String emailAddress, String password);


    UsersDTO createUser(UsersDTO usersDTO);

    Users updateUser1(UUID userId, UsersDTO updatedUser);

    void deleteUser(UUID userId);

    Users updateUser(UUID id, String username, String emailAddress, String password);
}
