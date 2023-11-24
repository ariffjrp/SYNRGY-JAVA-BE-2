package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.model.ERole;
import binarfud.Challenge_5.model.Users;
import binarfud.Challenge_5.model.dto.UsersDTO;
import binarfud.Challenge_5.repository.UserRepository;
import binarfud.Challenge_5.service.OtpService;
import binarfud.Challenge_5.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OtpService otpService;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable UUID id,
                                                       Authentication authentication, Principal principal){
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

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UsersDTO usersDTO) {
        try {
            Map<String, Object> response = new HashMap<>();
//            userRepository.createUserAccount(usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword());
            UsersDTO newUserDTO = userService.createUser(usersDTO);
//            UsersDTO newUserDTO = new UsersDTO(usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword());

            Set<ERole> selectedRole = usersDTO.getRole();

            usersDTO.setRole(selectedRole);


            boolean otpSent = otpService.generateOtp(newUserDTO.getUsername());
            if (otpSent) {
                response.put("Message", "User was created successfully, OTP sent to the registered email");
                response.put("Data", newUserDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("Message", "Failed to send OTP, user created without OTP verification");
                response.put("Data", newUserDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error(String.valueOf(e));
            response.put("Message", "Failed to create Users. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestParam String email, @RequestParam Integer otp) {
        try {
            Map<String, Object> response = new HashMap<>();
            Boolean isOtpValid = otpService.verifyOtp(email, otp);

            if (isOtpValid) {
                // Lakukan konfirmasi bahwa OTP telah diverifikasi dan proses pendaftaran dapat dilanjutkan
                response.put("Message", "OTP verification successful.");
                return ResponseEntity.ok(response);
            } else {
                response.put("Message", "Invalid OTP.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            logger.error("Error verifying OTP: " + e);
            response.put("Message", "Failed to verify OTP. Please check application log.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable UUID id,
            @RequestBody UsersDTO usersDTO,
            Authentication authentication, Principal principal
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
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable UUID id,
                                                          Authentication authentication, Principal principal) {
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
