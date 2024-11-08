package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.dto.Book;
import edu.icet.dto.Member;
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
    private   Integer borrowId;
    private Integer id;
    private Integer memberId;


    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private  Date issueDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date returnDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private double fine;

}
