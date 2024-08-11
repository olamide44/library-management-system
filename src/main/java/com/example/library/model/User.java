package com.example.library.model;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Remember to hash this before storing

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String address;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Column(nullable = false)
    private boolean isActive;

    @Column
    private LocalDate lastLoginDate;

    @Column
    private int borrowedBooksCount;

    @Column
    private double finesOwed;

    public enum UserRole {
        MEMBER, LIBRARIAN, ADMIN
    }
}