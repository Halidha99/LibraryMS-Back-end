package edu.icet.service;


import edu.icet.dto.Member;

import java.util.List;


public interface MemberService {
    List<Member> getAll();
    void addMember(Member member);
    void deleteMember(Integer id);
    Member searchMemberById(Integer id);
    void updateBMemberById(Member member);
    //List<Member>searchByName(String name);
}
