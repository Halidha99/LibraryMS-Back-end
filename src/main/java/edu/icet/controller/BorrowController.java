package edu.icet.controller;

import edu.icet.dto.Borrow;
import edu.icet.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
@CrossOrigin
public class BorrowController {

    private final BorrowService borrowService;


    @GetMapping("/get-borrow")
    public List<Borrow> getAllBorrow() {
        return borrowService.getAll();

    }


    @PostMapping("/add-borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBorrow(@RequestBody Borrow borrow) {
        borrowService.addBorrow(borrow);
    }



    @PutMapping("/update-borrow")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBorrow(@RequestBody Borrow borrow) {
    borrowService.updateBorrow(borrow);

    }
    @DeleteMapping("/delete-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBorrowById(@PathVariable Integer id) {
        borrowService.deleteBorrow(id);
    }

    @PutMapping("/borrow/mark-returned/{id}")
    public ResponseEntity<Void> markReturned(@PathVariable Integer id) {
        boolean isUpdated = borrowService.markAsReturned(id);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/calculate-overdue/{borrowId}")
    public ResponseEntity<String> calculateOverdue(@PathVariable Integer borrowId) {
        String overdueStatus = borrowService.calculateOverdue(borrowId);
        if ("Borrow record not found".equals(overdueStatus)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(overdueStatus);
        }
        return ResponseEntity.status(HttpStatus.OK).body(overdueStatus);


    }
    }
