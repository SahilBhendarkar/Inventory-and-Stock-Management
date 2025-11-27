
package com.Sahil.inventory_management.ServiceImpl;

import com.Sahil.inventory_management.DTO.LoginRequest;
import com.Sahil.inventory_management.DTO.LoginResponse;
import com.Sahil.inventory_management.DTO.RegisterRequest;
import com.Sahil.inventory_management.Repository.AdminRepository;
import com.Sahil.inventory_management.Repository.CustomerRepository;
import com.Sahil.inventory_management.Repository.DealerRepository;
import com.Sahil.inventory_management.Repository.UserRepository;
import com.Sahil.inventory_management.Service.DealerUserProjection;
import com.Sahil.inventory_management.Service.IUserService;
import com.Sahil.inventory_management.Util.JwtUtil;
import com.Sahil.inventory_management.model.Admin;
import com.Sahil.inventory_management.model.Customer;
import com.Sahil.inventory_management.model.Dealer;
import com.Sahil.inventory_management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtil jwtUtil;


    // -------------------- Register New User --------------------
    @Override
    public ResponseEntity<String> registerUser(RegisterRequest request) {

        String role = request.getRole().toUpperCase();

        if (!role.equals("CUSTOMER") && !role.equals("ADMIN") && !role.equals("DEALER")) {
            return ResponseEntity.badRequest()
                    .body("Invalid role. Must be CUSTOMER, ADMIN, or DEALER.");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email already registered.");
        }

        // Create and save user
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

        // Save into respective role table
        switch (role) {
            case "ADMIN" -> adminRepository.save(new Admin(user));

            case "DEALER" -> dealerRepository.save(new Dealer(user));

            case "CUSTOMER" -> customerRepository.save(new Customer(user));

        }

        return ResponseEntity.ok("User registered successfully as " + role);
    }

    // -------------------- Login User --------------------
    @Override
    public ResponseEntity<?> loginUser(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (user.getPassword().equals(request.getPassword())) { // Plain password check
                        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getName(), user.getRole());
                        return ResponseEntity.ok(new LoginResponse(token, user.getRole()));
                    } else {
                        return ResponseEntity.status(401).body("Invalid password");
                    }
                })
                .orElse(ResponseEntity.status(401).body("User not found"));
    }



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public List<DealerUserProjection> getAllDealers() {
        return dealerRepository.getAllDealerUsers();
    }


}
