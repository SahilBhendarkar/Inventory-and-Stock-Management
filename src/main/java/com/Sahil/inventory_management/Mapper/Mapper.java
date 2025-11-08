package com.Sahil.inventory_management.Mapper;

import com.Sahil.inventory_management.DTO.ProductDTO;
import com.Sahil.inventory_management.DTO.UserDTO;
import com.Sahil.inventory_management.model.Product;
import com.Sahil.inventory_management.model.User;

public class Mapper {

    // ---------------- USER: Entity → DTO ----------------
    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),     // INCLUDED
                user.getRole(),
                user.getMobileNo(),
                user.getAddress(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // ---------------- USER: DTO → Entity ----------------
    public static User toUser(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());  // INCLUDED
        user.setRole(dto.getRole());
        user.setMobileNo(dto.getMobileNo());
        user.setAddress(dto.getAddress());
        user.setStatus(dto.getStatus());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        return user;
    }

    // ---------------- PRODUCT: Entity → DTO ----------------
    public static ProductDTO toProductDTO(Product product) {
        if (product == null) return null;
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getBrand(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getMinStockLevel(),
                product.getDealerId(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    // ---------------- PRODUCT: DTO → Entity ----------------
    public static Product toProduct(ProductDTO dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setMinStockLevel(dto.getMinStockLevel());
        product.setDealerId(dto.getDealerId());
        product.setCreatedAt(dto.getCreatedAt());
        product.setUpdatedAt(dto.getUpdatedAt());
        return product;
    }

}