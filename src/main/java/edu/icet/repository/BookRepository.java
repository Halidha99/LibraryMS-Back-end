package edu.icet.repository;

import edu.icet.dto.Book;
import edu.icet.entity.BookEntity;
import edu.icet.entity.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<BookEntity,Integer> {
    List<BookEntity> findByBookName(String bookName);
//    List<BookEntity> findByIsReturnedFalse();



}
