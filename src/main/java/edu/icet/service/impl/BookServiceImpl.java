package edu.icet.service.impl;

import edu.icet.dto.Book;
import edu.icet.entity.BookEntity;
import edu.icet.repository.BookRepository;
import edu.icet.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final ModelMapper mapper;
    private final BookRepository repository;

    @Override
    public List<Book> getAll() {

        return repository.findAll().stream()
                .map(entity -> mapper.map(entity, Book.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addBook(Book book) {

        BookEntity bookEntity = mapper.map(book, BookEntity.class);
        repository.save(bookEntity);
    }

    @Override
    public void deleteBook(Integer id) {

        repository.deleteById(id);
    }

    @Override
    public Book searchBookById(Integer id) {

        Optional<BookEntity> bookEntity = repository.findById(id);
        return bookEntity.map(entity -> mapper.map(entity, Book.class))
                .orElse(null);
    }

    @Override
    public void updateBookById(Book book) {

        BookEntity bookEntity = mapper.map(book, BookEntity.class);
        repository.save(bookEntity);
    }

    @Override
    public List<Book> searchByName(String bookName) {
        List<Book> books= new ArrayList<>();
        repository.findByBookName(bookName).forEach(entity->{
            books.add(mapper.map(entity,Book.class));
        });

        return books;
    }


}
