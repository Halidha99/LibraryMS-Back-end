package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "borrow")
public class BorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer borrowId;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date returnDate;

//    @Temporal(TemporalType.DATE)
//    private Date dueDate;
//
//    private double fine;

}
