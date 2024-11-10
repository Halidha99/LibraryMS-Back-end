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
import java.util.Optional;
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
        BookEntity bookEntity = repository.findById(borrow.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + borrow.getBook().getId()));

        MemberEntity memberEntity = memberRepository.findById(borrow.getMember().getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + borrow.getMember().getMemberId()));

        if (bookEntity.getQty() > 0) {
            bookEntity.setQty(bookEntity.getQty() - 1);
            repository.save(bookEntity);

            BorrowEntity borrowEntity = mapper.map(borrow, BorrowEntity.class);
            borrowEntity.setBook(bookEntity);
            borrowEntity.setMember(memberEntity);
            borrowRepository.save(borrowEntity);
        } else {
            throw new IllegalStateException("Book is out of stock.");
        }
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

        if (borrow.getReturnDate() != null) {
            existingBorrowEntity.setReturnDate(borrow.getReturnDate());
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
        borrowEntity.setReturned(true);

        double fine = calculateFine(borrowEntity);
        borrowEntity.setFine(fine);

        borrowRepository.save(borrowEntity);
    }

    @Override
    public void deleteBorrow(Integer id) {
        borrowRepository.deleteById(id);
    }


    @Override
    public String calculateOverdue(Integer borrowId) {
        Optional<BorrowEntity> borrowEntityOptional = borrowRepository.findById(borrowId);
        if (borrowEntityOptional.isPresent()) {
            BorrowEntity borrowEntity = borrowEntityOptional.get();
            if (!borrowEntity.isReturned()) {

                borrowEntity.setFine(calculateFine(borrowEntity));
                borrowRepository.save(borrowEntity);
                return "Overdue - Fine: " + borrowEntity.getFine();
            } else {
                return "Book already returned.";
            }
        } else {
            throw new IllegalArgumentException("Borrow record not found.");
        }
    }

    @Override
    public boolean markAsReturned(Integer borrowId) {
        BorrowEntity borrow = borrowRepository.findById(borrowId).orElse(null);

        if (borrow == null) {
            return false;
        }

        if (borrow.isReturned()) {

            return false;
        }


        borrow.setReturned(true);

        borrowRepository.save(borrow);
        return true;
    }

    private double calculateFine(BorrowEntity borrowEntity) {
        long overdueDays = getOverdueDays(borrowEntity);
        if (overdueDays > 0) {
            return overdueDays * 2.0;
        }
        return 0.0;
    }

    private long getOverdueDays(BorrowEntity borrowEntity) {
        if (borrowEntity.getReturnDate() == null) {
            return 0;
        }
        long diffInMillis = System.currentTimeMillis() - borrowEntity.getDueDate().getTime();
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
        return diffInDays > 0 ? diffInDays : 0;
    }
}
