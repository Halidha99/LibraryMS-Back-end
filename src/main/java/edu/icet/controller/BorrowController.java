package edu.icet.controller;

import edu.icet.dto.Borrow;
import edu.icet.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
@CrossOrigin
public class BorrowController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowController.class);

    private final BorrowService borrowService;


    @GetMapping("/get-borrow")
    public List<Borrow> getAllBorrow() {
        try {
            return borrowService.getAll();
        } catch (Exception e) {
            logger.error("Error fetching borrow records: {}", e.getMessage());
            throw new RuntimeException("Error fetching borrow records.");
        }
    }


    @PostMapping("/add-borrow")
    public ResponseEntity<String> addBorrow(@RequestBody Borrow borrow) {
        try {

            if (borrow.getBook() == null || borrow.getMember() == null) {
                logger.warn("Book or Member not provided: Book: {}, Member: {}", borrow.getBook(), borrow.getMember());
                return ResponseEntity.badRequest().body("Book and Member must be provided.");
            }


            borrowService.addBorrow(borrow);
            logger.info("Book issued successfully: Book ID: {}, Member ID: {}", borrow.getBook().getId(), borrow.getMember().getMemberId());
            return ResponseEntity.ok("Book issued successfully.");
        } catch (IllegalArgumentException e) {

            logger.error("Validation error while issuing book: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {

            logger.error("Unexpected error while issuing book: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @PutMapping("/update-borrow")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> updateBorrow(@RequestBody Borrow borrow) {
        try {

            if (borrow.getBorrowId() == null) {
                return ResponseEntity.badRequest().body("Borrow ID is required for update.");
            }

            borrowService.updateBorrow(borrow);
            logger.info("Borrow record updated successfully: Borrow ID: {}", borrow.getBorrowId());
            return ResponseEntity.ok("Borrow record updated successfully.");
        } catch (IllegalArgumentException e) {

            logger.error("Validation error while updating borrow record: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while updating borrow record: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

}
