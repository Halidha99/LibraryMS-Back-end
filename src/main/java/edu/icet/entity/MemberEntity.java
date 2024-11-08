package edu.icet.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member")

public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id",length = 11)
    private Integer memberId;
    @Column(name = "member_name",length = 50)
    private String name;
    private String address;
    private String email;
}
