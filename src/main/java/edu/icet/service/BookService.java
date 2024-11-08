package edu.icet.service;

import edu.icet.dto.Book;

import java.util.List;

public interface BookService {
    List<Book>getAll();
    void addBook(Book book);
    void deleteBook(Integer id);
    Book searchBookById(Integer id);
    void updateBookById(Book book);

    List<Book> searchByName(String name);

    //  List<Book> searchByName(String bookName);
//    List <Book> searchByName(String name);

}
