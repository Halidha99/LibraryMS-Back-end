package edu.icet.service;

import edu.icet.dto.Borrow;

import java.util.List;

public interface BorrowService {
    List<Borrow> getAll();
    void addBorrow(Borrow borrow);

    void updateBorrow(Borrow borrow);
    void returnBorrowBook(Integer borrowId);

}
