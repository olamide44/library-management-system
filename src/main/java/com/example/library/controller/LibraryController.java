package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.Loan;
import com.example.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // User related endpoints
    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(libraryService.registerUser(user));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(libraryService.getUserById(id));
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String name) {
        return ResponseEntity.ok(libraryService.searchUsers(name));
    }

    // Book related endpoints
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(libraryService.addBook(book));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(libraryService.getBookById(id));
    }

    // Loan related endpoints
    @PostMapping("/loans")
    public ResponseEntity<Loan> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(libraryService.borrowBook(userId, bookId));
    }

    @PutMapping("/loans/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(libraryService.returnBook(id));
    }

    @GetMapping("/loans/overdue")
    public ResponseEntity<List<Loan>> getOverdueLoans() {
        return ResponseEntity.ok(libraryService.getOverdueLoans());
    }

    // Add more endpoints as needed
}