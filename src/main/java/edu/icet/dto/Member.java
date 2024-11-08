package edu.icet.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Member {
    private Integer MemberId;
    private String name;
    private String address;
    private String email;

}
