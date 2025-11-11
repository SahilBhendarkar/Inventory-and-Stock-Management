package com.Sahil.inventory_management.Service;

import com.Sahil.inventory_management.DTO.LoginRequest;
import com.Sahil.inventory_management.DTO.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<String> registerUser(RegisterRequest request);

    ResponseEntity<?> loginUser(LoginRequest request);
}
