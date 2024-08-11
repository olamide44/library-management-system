package com.example.library.repository;

import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByLastName(String lastName);

    List<User> findByRole(User.UserRole role);

    @Query("SELECT u FROM User u WHERE u.registrationDate >= :startDate AND u.registrationDate <= :endDate")
    List<User> findUsersRegisteredBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<User> findByIsActive(boolean isActive);

    @Query("SELECT u FROM User u WHERE u.borrowedBooksCount > 0")
    List<User> findUsersWithBorrowedBooks();

    @Query("SELECT u FROM User u WHERE u.finesOwed > 0")
    List<User> findUsersWithOutstandingFines();

    @Query("SELECT u FROM User u WHERE u.lastLoginDate < :date")
    List<User> findInactiveUsers(@Param("date") LocalDate date);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> searchUsersByName(@Param("name") String name);
}