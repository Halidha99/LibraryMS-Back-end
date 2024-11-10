package edu.icet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")

public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id",length = 11)
    private Integer id;
    @Column(name = "bookName",length = 50)
    private String bookName;
    private String authorName;
    private String category;
    private Integer qty;
    private boolean returned;


//    @OneToMany(mappedBy = "book")
//    private Set<BorrowEntity> borrowEntities;

    public void borrowBook() {
        if (this.qty > 0) {
            this.qty--;
        } else {
            throw new IllegalStateException("No copies available to borrow.");
        }
    }


    public void returnBook() {
        this.qty++;
    }
}
