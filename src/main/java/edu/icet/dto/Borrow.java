package edu.icet.dto;

import lombok.*;


import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Borrow {
        private Integer borrowId;
        private Book book;
        private Member member;
        private Date issueDate;
        private Date returnDate;
//        private Date dueDate;
//        private double fine;



}
