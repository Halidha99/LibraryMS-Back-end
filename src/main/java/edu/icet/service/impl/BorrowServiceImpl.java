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

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final BookRepository repository;
    private final BorrowRepository borrowRepository;
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


        BookEntity bookEntity = repository.findById(borrow.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + borrow.getBook().getId()));

        MemberEntity memberEntity = memberRepository.findById(borrow.getMember().getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + borrow.getMember().getMemberId()));


        BorrowEntity borrowEntity = mapper.map(borrow, BorrowEntity.class);


        borrowEntity.setId(bookEntity.getId());
        borrowEntity.setMemberId(memberEntity.getMemberId());


//        System.out.println("Book ID: " + borrowEntity.getId());
//        System.out.println("Member ID: " + borrowEntity.getMemberId());
//        System.out.println("Issue Date: " + borrowEntity.getIssueDate());
//        System.out.println("Return Date: " + borrowEntity.getReturnDate());
//        System.out.println("Due Date: " + borrowEntity.getDueDate());


        borrowRepository.save(borrowEntity);

    }



    @Override
    public void updateBorrow(Borrow borrow) {

    }
}
