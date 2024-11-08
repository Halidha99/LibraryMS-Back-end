package edu.icet.controller;

import edu.icet.dto.Borrow;
import edu.icet.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
