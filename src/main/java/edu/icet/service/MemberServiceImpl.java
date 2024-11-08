package edu.icet.service;


import edu.icet.dto.Member;

import edu.icet.entity.MemberEntity;
import edu.icet.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;



@Service
@CrossOrigin
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository MemberRepository;
    private final ModelMapper mapper;

    @Override
    public List<Member> getAll() {
        List<Member> memberArrayList = new ArrayList<>();
        MemberRepository.findAll().forEach(entity -> {
            memberArrayList.add(mapper.map(entity, Member.class));
        });
        return memberArrayList;
    }

    @Override
    public void addMember(Member member) {
        MemberRepository.save(mapper.map(member, MemberEntity.class));
    }

    @Override
    public void deleteMember(Integer id) {
        MemberRepository.deleteById(id);
    }

    @Override
    public Member searchMemberById(Integer id) {
        return mapper.map(MemberRepository.findById(id), Member.class);
    }

    @Override
    public void updateBMemberById(Member member) {
        MemberRepository.save(mapper.map(member, MemberEntity.class));
    }
}
