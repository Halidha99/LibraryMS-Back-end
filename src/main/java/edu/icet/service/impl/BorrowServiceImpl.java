package edu.icet.service.impl;

import edu.icet.dto.Borrow;
import edu.icet.entity.BookEntity;
import edu.icet.entity.BorrowEntity;
import edu.icet.entity.MemberEntity;
import edu.icet.repository.BookRepository;
import edu.icet.repository.BorrowRepository;
import edu.icet.repository.MemberRepository;
import edu.icet.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final ModelMapper mapper;
    private final BookRepository repository;
    private final BorrowRepository borrowRepository;
    private final MemberRepository memberRepository;
    @Override
    public List<Borrow> getAll() {
        return borrowRepository.findAll().stream()
                .map(entity -> mapper.map(entity, Borrow.class))
                .collect(Collectors.toList());

    }

    @Override
    public void addBorrow(Borrow borrow) {
        if (borrow == null) {
            throw new IllegalArgumentException("Borrow object cannot be null");
        }
        if (borrow.getBook() == null || borrow.getMember() == null) {
            throw new IllegalArgumentException("Book and Member must be provided");
        }

        // Fetch the Book and Member entities
        BookEntity bookEntity = repository.findById(borrow.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + borrow.getBook().getId()));

        MemberEntity memberEntity = memberRepository.findById(borrow.getMember().getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + borrow.getMember().getMemberId()));


        if (bookEntity.getQty() > 0) {
            bookEntity.setQty(bookEntity.getQty() - 1);
            repository.save(bookEntity);

            // Map the Borrow DTO to BorrowEntity
            BorrowEntity borrowEntity = mapper.map(borrow, BorrowEntity.class);
            borrowEntity.setBook(bookEntity);
            borrowEntity.setMember(memberEntity);

            borrowRepository.save(borrowEntity);
        } else {
            throw new IllegalStateException("Book is out of stock.");
        }



//        System.out.println("Book ID: " + borrowEntity.getId());
//        System.out.println("Member ID: " + borrowEntity.getMemberId());
//        System.out.println("Issue Date: " + borrowEntity.getIssueDate());
//        System.out.println("Return Date: " + borrowEntity.getReturnDate());
//        System.out.println("Due Date: " + borrowEntity.getDueDate());




    }



    @Override
    public void updateBorrow(Borrow borrow) {
        if (borrow == null) {
            throw new IllegalArgumentException("Borrow object cannot be null");
        }


        BorrowEntity existingBorrowEntity = borrowRepository.findById(borrow.getBorrowId())
                .orElseThrow(() -> new IllegalArgumentException("Borrow record not found with ID: " + borrow.getBorrowId()));


        if (borrow.getIssueDate() != null) {
            existingBorrowEntity.setIssueDate(borrow.getIssueDate());
        }

        if (borrow.getDueDate() != null) {
            existingBorrowEntity.setDueDate(borrow.getDueDate());
        }

        if (borrow.getReturnDate() != null) {
            existingBorrowEntity.setReturnDate(borrow.getReturnDate());
        }


        if (borrow.getFine() > 0) {
            existingBorrowEntity.setFine(borrow.getFine());
        }


        if (borrow.getBook() != null) {
            BookEntity bookEntity = repository.findById(borrow.getBook().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + borrow.getBook().getId()));
            existingBorrowEntity.setBook(bookEntity);
        }

        if (borrow.getMember() != null) {
            MemberEntity memberEntity = memberRepository.findById(borrow.getMember().getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + borrow.getMember().getMemberId()));
            existingBorrowEntity.setMember(memberEntity);
        }


        borrowRepository.save(existingBorrowEntity);
    }

    @Override
    public void returnBorrowBook(Integer borrowId) {
        BorrowEntity borrowEntity = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new IllegalArgumentException("Borrow record not found with ID: " + borrowId));


        BookEntity bookEntity = borrowEntity.getBook();


        bookEntity.setQty(bookEntity.getQty() + 1);
        repository.save(bookEntity);


        borrowEntity.setReturnDate(new Date());
        borrowRepository.save(borrowEntity);
    }
}
