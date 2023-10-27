package binarfud.Challenge_4.controller;

import binarfud.Challenge_4.model.Users;
import binarfud.Challenge_4.model.UsersDTO;
import binarfud.Challenge_4.repository.UserRepository;
import binarfud.Challenge_4.service.UserService;
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
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO) {
        try {
            userRepository.createUserAccount(usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword());
            UsersDTO newUserDTO = new UsersDTO(usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword());
            logger.info(String.valueOf(newUserDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Users> updateUser(
            @PathVariable UUID id,
            @RequestBody UsersDTO usersDTO // Anda mungkin perlu menggunakan DTO untuk menerima data
    ) {
        try {
            Users updatedUser = userService.updateUser(id, usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword());
            logger.info("User updated successfully: " + updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            logger.info("User Removed");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
