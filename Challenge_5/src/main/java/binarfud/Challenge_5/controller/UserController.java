package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.model.Users;
import binarfud.Challenge_5.model.UsersDTO;
import binarfud.Challenge_5.repository.UserRepository;
import binarfud.Challenge_5.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable UUID id){
        try {
            Map<String, Object> response = new HashMap<>();
            Users users = userService.getUserById(id);

            response.put("Message", "Users By Id was retrieved successfully");
            response.put("Data", users);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Failed to Find Data Users. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UsersDTO usersDTO) {
        try {
            Map<String, Object> response = new HashMap<>();
//            userRepository.createUserAccount(usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword());
            UsersDTO newUserDTO = userService.createUser(usersDTO);
//            UsersDTO newUserDTO = new UsersDTO(usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword());
            logger.info(String.valueOf(newUserDTO));
            response.put("Message", "User was created successfully");
            response.put("Data", newUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to create Users. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable UUID id,
            @RequestBody UsersDTO usersDTO
    ) {
        try {
            Map<String, Object> response = new HashMap<>();
            Users updatedUser = userService.updateUser1(id, usersDTO);
            logger.info("User updated successfully: " + updatedUser);
            response.put("Message", "User was update successfully");
            response.put("Data", updatedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error("Error updating user: " + e);
            response.put("Message", "Failed to update Users. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable UUID id) {
        try {
            Map<String, Object> response = new HashMap<>();
            userService.deleteUser(id);
            response.put("Message", "User was delete successfully");
            logger.info("User Removed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to delete Users. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
