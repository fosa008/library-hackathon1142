package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/books")
    public List<Map<String, Object>> getBooks() {
        return jdbcTemplate.queryForList("SELECT * FROM books");
    }

    @GetMapping("/borrowed")
    public List<Map<String, Object>> getBorrowedBooks() {
        return jdbcTemplate.queryForList(
            "SELECT br.id, b.title, br.borrower_name, br.borrow_date " +
            "FROM borrow_records br JOIN books b ON br.book_id = b.id"
        );
    }

    @PostMapping("/borrow")
    public String borrowBook(@RequestParam int bookId, @RequestParam String borrowerName) {
        String today = LocalDate.now().toString();
        jdbcTemplate.update(
            "INSERT INTO borrow_records (book_id, borrower_name, borrow_date) VALUES (?, ?, ?)",
            bookId, borrowerName, today
        );
        return "Book successfully borrowed!";
    }

    @GetMapping("/borrowers/{name}/type")
    public Map<String, Object> getBorrowerType(@PathVariable String name) {
        
        Integer totalBorrows = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM borrow_records WHERE LOWER(borrower_name) = LOWER(?)",
            Integer.class, name
        );

        if (totalBorrows == null || totalBorrows == 0) {
            return Map.of(
                "borrowerName", name,
                "totalBorrows", 0,
                "distinctBooks", 0,
                "singleGenrePct", 0.0,
                "readerType", "Casual Reader",
                "topGenre", "None",
                "reasoning", "No prior borrowing histories found in relational tracking charts."
            );
        }

        Integer distinctBooks = jdbcTemplate.queryForObject(
            "SELECT COUNT(DISTINCT book_id) FROM borrow_records WHERE LOWER(borrower_name) = LOWER(?)",
            Integer.class, name
        );
        if (distinctBooks == null) distinctBooks = 0;

        List<Map<String, Object>> genreCounts = jdbcTemplate.queryForList(
            "SELECT b.genre, COUNT(*) as count " +
            "FROM borrow_records br JOIN books b ON br.book_id = b.id " +
            "WHERE LOWER(br.borrower_name) = LOWER(?) " +
            "GROUP BY b.genre ORDER BY count DESC LIMIT 1",
            name
        );

        int maxGenreCount = 0;
        String topGenre = "None";
        
        if (!genreCounts.isEmpty()) {
            Map<String, Object> row = genreCounts.get(0);
            Object countObj = row.get("count") != null ? row.get("count") : row.get("COUNT");
            if (countObj instanceof Number) {
                maxGenreCount = ((Number) countObj).intValue();
            }
            Object genreObj = row.get("genre") != null ? row.get("genre") : row.get("GENRE");
            if (genreObj != null) {
                topGenre = genreObj.toString();
            }
        }

        double singleGenrePct = ((double) maxGenreCount / totalBorrows) * 100.0;

        String readerType;
        String reasoning;

        if (singleGenrePct >= 70.0) { 
            if (totalBorrows >= 10) {
                readerType = "Fanatic Reader";
                reasoning = "🎯 Root Split (Genre Focus ≥ 70%): Highly focused on " + topGenre + " (" + String.format("%.1f", singleGenrePct) + "%). Sub-split (Volume ≥ 10): Deep domain specialist.";
            } else {
                readerType = "Specialist Reader";
                reasoning = "🎯 Root Split (Genre Focus ≥ 70%): Highly focused on " + topGenre + " (" + String.format("%.1f", singleGenrePct) + "%). Sub-split (Volume < 10): Selective interest tracker.";
            }
        } else {
            if (distinctBooks >= 5) {
                readerType = "Scholar Reader";
                reasoning = "🎯 Root Split (Genre Focus < 70%): Broad explorer. Sub-split (Distinct Books = " + distinctBooks + " ≥ 5): High-volume general explorer.";
            } else {
                readerType = "Casual Reader";
                reasoning = "🎯 Root Split (Genre Focus < 70%): Broad explorer. Sub-split (Distinct Books = " + distinctBooks + " < 5): Low engagement variety browser.";
            }
        }

        return Map.of(
            "borrowerName", name,
            "totalBorrows", totalBorrows,
            "distinctBooks", distinctBooks,
            "singleGenrePct", singleGenrePct,
            "readerType", readerType,
            "topGenre", topGenre,
            "reasoning", reasoning
        );
    }

    @GetMapping("/borrowers/{name}/recommendations")
    public List<Map<String, Object>> getRecommendations(@PathVariable String name) {
        Map<String, Object> typeData = getBorrowerType(name);
        String readerType = (String) typeData.get("readerType");
        String topGenre = (String) typeData.get("topGenre");

        if (readerType.equals("Fanatic Reader") || readerType.equals("Specialist Reader")) {
            return jdbcTemplate.queryForList("SELECT * FROM books WHERE genre = ? LIMIT 3", topGenre);
        } else {
            return jdbcTemplate.queryForList("SELECT * FROM books WHERE genre IN ('Business', 'Popular Science') LIMIT 3");
        }
    }
}