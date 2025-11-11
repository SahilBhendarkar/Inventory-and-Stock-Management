package com.Sahil.inventory_management.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String role;
    private String mobileNo;
    private String address;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    // ---------------- Constructors ----------------

    public User() {
    }

    public User(Long id, String name, String email, String password, String role, String mobileNo, String address, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.mobileNo = mobileNo;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ---------------- Getters & Setters ----------------


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    @Column(unique = true, nullable = false)
//    private String email;
//    private String password;
//    private String role;   // ADMIN / DEALER / CUSTOMER
//    private String mobileNo;
//    private String address;
//    private String status;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    // Optional: For bidirectional mapping with role tables
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Admin admin;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Dealer dealer;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Customer customer;
//
//    // ---------------- Constructors ----------------
//
//    public User() {}
//
//    public User(Long id, String name, String email, String password, String role, String mobileNo,
//                String address, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//        this.mobileNo = mobileNo;
//        this.address = address;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
//
//    // ---------------- Getters & Setters ----------------
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getRole() { return role; }
//    public void setRole(String role) { this.role = role; }
//
//    public String getMobileNo() { return mobileNo; }
//    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
//
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//
//    public LocalDateTime getUpdatedAt() { return updatedAt; }
//    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
//
//    public Admin getAdmin() { return admin; }
//    public void setAdmin(Admin admin) { this.admin = admin; }
//
//    public Dealer getDealer() { return dealer; }
//    public void setDealer(Dealer dealer) { this.dealer = dealer; }
//
//    public Customer getCustomer() { return customer; }
//    public void setCustomer(Customer customer) { this.customer = customer; }
//}
