package com.example.library.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime loanDateTime;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDateTime returnDateTime;

    @Column(nullable = false)
    private boolean isOverdue;

    @Column(nullable = false)
    private BigDecimal fineAmount;

    @Column(nullable = false)
    private boolean isPaid;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "issued_by")
    private User issuedBy;

    @ManyToOne
    @JoinColumn(name = "received_by")
    private User receivedBy;

    @Column(nullable = false)
    private int renewalCount;

    @Column
    private LocalDate lastRenewalDate;

    public enum LoanStatus {
        ACTIVE, RETURNED, LOST, DAMAGED
    }
}