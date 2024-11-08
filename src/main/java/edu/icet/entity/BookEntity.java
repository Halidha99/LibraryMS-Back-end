package edu.icet.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
