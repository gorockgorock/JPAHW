package com.sparta.travelnewsfeed.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    // Other fields like email, profile information, etc.

    // Standard getters and setters...
}
