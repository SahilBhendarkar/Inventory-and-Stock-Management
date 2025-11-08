package com.Sahil.inventory_management.Controllers;


import com.Sahil.inventory_management.DTO.LoginRequest;
import com.Sahil.inventory_management.DTO.LoginResponse;
import com.Sahil.inventory_management.DTO.RegisterRequest;
import com.Sahil.inventory_management.Repository.UserRepository;
import com.Sahil.inventory_management.Util.JwtUtil;
import com.Sahil.inventory_management.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    //  Registering a new User
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {


        String role = request.getRole().toUpperCase();
        if (!role.equals("CUSTOMER") && !role.equals("ADMIN") && !role.equals("DEALER")) {
            return ResponseEntity.badRequest()
                    .body("Invalid role. Must be CUSTOMER, ADMIN, or DEALER.");
        }


        // It Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email already registered.");
        }


        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(role);
        user.setMobileNo(request.getMobileNo());
        user.setAddress(request.getAddress());
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully as " + role);
    }


    //  Login for a User
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (user.getPassword().equals(request.getPassword())) { // Plain text check
                        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
                        return ResponseEntity.ok(new LoginResponse(token, user.getRole()));
                    } else {
                        return ResponseEntity.status(401).body("Invalid password");
                    }
                })
                .orElse(ResponseEntity.status(401).body("User not found"));
    }

}
