package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.Loan;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    // User related methods
    public User registerUser(User user) {
        user.setRegistrationDate(LocalDate.now());
        user.setActive(true);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> searchUsers(String name) {
        return userRepository.searchUsersByName(name);
    }

    // Book related methods
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // Loan related methods
    @Transactional
    public Loan borrowBook(Long userId, Long bookId) {
        User user = getUserById(userId);
        Book book = getBookById(bookId);

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDateTime(LocalDateTime.now());
        loan.setDueDate(LocalDate.now().plusWeeks(2));
        loan.setStatus(Loan.LoanStatus.ACTIVE);

        user.setBorrowedBooksCount(user.getBorrowedBooksCount() + 1);
        userRepository.save(user);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != Loan.LoanStatus.ACTIVE) {
            throw new RuntimeException("Book already returned or lost");
        }

        loan.setReturnDateTime(LocalDateTime.now());
        loan.setStatus(Loan.LoanStatus.RETURNED);

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        User user = loan.getUser();
        user.setBorrowedBooksCount(user.getBorrowedBooksCount() - 1);
        userRepository.save(user);

        return loanRepository.save(loan);
    }

    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans();
    }

    public BigDecimal calculateFine(Loan loan) {
        if (loan.getStatus() != Loan.LoanStatus.ACTIVE || !loan.isOverdue()) {
            return BigDecimal.ZERO;
        }

        long daysOverdue = LocalDate.now().toEpochDay() - loan.getDueDate().toEpochDay();
        return new BigDecimal(daysOverdue).multiply(new BigDecimal("0.50")); // $0.50 per day
    }

    // Add more methods as needed
}