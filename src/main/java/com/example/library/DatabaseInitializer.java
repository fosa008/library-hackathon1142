package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("Initializing SQLite Database Tables...");
            
            // 1. Create Tables
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, genre TEXT NOT NULL, author TEXT);");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS borrow_records (id INTEGER PRIMARY KEY AUTOINCREMENT, book_id INTEGER NOT NULL, borrower_name TEXT NOT NULL, borrow_date DATE NOT NULL);");

            // 2. Check if already seeded
            Integer bookCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM books", Integer.class);
            if (bookCount == 0) {
                System.out.println("Seeding database with books and borrowing records...");
                
                // Seed Books
                jdbcTemplate.execute("INSERT INTO books (id, title, genre, author) VALUES (1, 'The Midnight Library', 'Fiction', 'Matt Haig');");
                jdbcTemplate.execute("INSERT INTO books (id, title, genre, author) VALUES (2, 'Project Hail Mary', 'Fiction', 'Andy Weir');");
                jdbcTemplate.execute("INSERT INTO books (id, title, genre, author) VALUES (11, 'A Brief History of Time', 'Popular Science', 'Stephen Hawking');");
                jdbcTemplate.execute("INSERT INTO books (id, title, genre, author) VALUES (12, 'Sapiens', 'Popular Science', 'Yuval Noah Harari');");
                jdbcTemplate.execute("INSERT INTO books (id, title, genre, author) VALUES (21, 'Atomic Habits', 'Business', 'James Clear');");
                jdbcTemplate.execute("INSERT INTO books (id, title, genre, author) VALUES (31, 'Guns, Germs, and Steel', 'History', 'Jared Diamond');");
                jdbcTemplate.execute("INSERT INTO books (id, title, genre, author) VALUES (41, 'Watchmen', 'Comics', 'Alan Moore');");

                // Seed Borrow Records
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (1, 'Alice', '2026-01-05');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (2, 'Alice', '2026-01-12');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (11, 'Bob', '2026-01-10');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (12, 'Bob', '2026-01-24');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (1, 'Frank', '2026-01-01');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (11, 'Frank', '2026-03-01');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (21, 'Frank', '2026-05-01');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (31, 'Frank', '2026-01-10');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (1, 'Kevin', '2026-01-15');");
                jdbcTemplate.execute("INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (1, 'Paul', '2026-01-10');");

                System.out.println("Database seeding completed successfully!");
            } else {
                System.out.println("Database already contains data. Skipping seeding step.");
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR during database initialization:");
            e.printStackTrace();
            throw e;
        }
    }
}