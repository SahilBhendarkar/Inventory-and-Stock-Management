package com.Sahil.inventory_management.Service;

import com.Sahil.inventory_management.DTO.LoginRequest;
import com.Sahil.inventory_management.DTO.RegisterRequest;
import com.Sahil.inventory_management.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    ResponseEntity<String> registerUser(RegisterRequest request);

    ResponseEntity<?> loginUser(LoginRequest request);

    List<User> getAllUsers();

    List<DealerUserProjection> getAllDealers();

}
