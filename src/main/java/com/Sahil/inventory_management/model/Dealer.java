package com.Sahil.inventory_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dealers")
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    //-------------- Constructors ---------------


    public Dealer() {
    }

    public Dealer(User user) {
        this.user = user;
    }

    //------------- Getter and Setter --------------


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
