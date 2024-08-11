package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUser(User user);

    List<Loan> findByBook(Book book);

    List<Loan> findByStatus(Loan.LoanStatus status);

    @Query("SELECT l FROM Loan l WHERE l.dueDate < CURRENT_DATE AND l.status = 'ACTIVE'")
    List<Loan> findOverdueLoans();

    @Query("SELECT l FROM Loan l WHERE l.user = :user AND l.status = 'ACTIVE'")
    List<Loan> findActiveLoansForUser(@Param("user") User user);

    @Query("SELECT l FROM Loan l WHERE l.loanDateTime >= :startDate AND l.loanDateTime <= :endDate")
    List<Loan> findLoansBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT l FROM Loan l WHERE l.isOverdue = true AND l.isPaid = false")
    List<Loan> findUnpaidOverdueLoans();

    @Query("SELECT l FROM Loan l WHERE l.book = :book AND l.status = 'ACTIVE'")
    Optional<Loan> findActiveLoansForBook(@Param("book") Book book);

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.user = :user AND l.status = 'ACTIVE'")
    int countActiveLoansForUser(@Param("user") User user);

    @Query("SELECT SUM(l.fineAmount) FROM Loan l WHERE l.user = :user AND l.isPaid = false")
    Double sumUnpaidFinesForUser(@Param("user") User user);

    @Query("SELECT l FROM Loan l WHERE l.renewalCount > 0")
    List<Loan> findRenewedLoans();

    @Query("SELECT l FROM Loan l WHERE l.lastRenewalDate >= :date")
    List<Loan> findLoansRenewedAfter(@Param("date") LocalDate date);
}