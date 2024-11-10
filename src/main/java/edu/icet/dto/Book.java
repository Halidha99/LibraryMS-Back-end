package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    private String bookName;
    private String authorName;
    private String category;
    private Integer qty;
    private boolean returned;

    public void borrowBook() {

        this.qty--;
    }

    public void returnBook() {

        this.qty++;
    }
}
