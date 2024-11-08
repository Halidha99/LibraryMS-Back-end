package edu.icet.controller;



import edu.icet.dto.Member;
import edu.icet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

 private final   MemberService memberService;
    @GetMapping("/get-member")
    public List<Member> getMember() {
        return memberService.getAll();
    }

    @PostMapping("/add-member")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMember(@RequestBody Member member) {
       memberService.addMember(member);
    }

    @GetMapping("/search-by-id/{id}")
    public Member getMemberById(@PathVariable Integer id) {
      return  memberService.searchMemberById(id);
    }

    @DeleteMapping("/delete-by-id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMemberById(@PathVariable Integer id) {
        memberService.deleteMember(id);
    }

    @PutMapping("/update-member")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateMemeber(@RequestBody Member member) {
        memberService.updateBMemberById(member);
    }
}
