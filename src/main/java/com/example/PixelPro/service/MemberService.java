package com.example.PixelPro.service;

import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired MemberRepository memberRepository;
    public void save(Member member)
    {
        memberRepository.save(member);
    }

    public boolean isEmailDuplicate(String email){
        return memberRepository.existsByEmail(email);
    }

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    /*주영 - select 작업*/
    public List<Member> findByOrderByMbnumDesc() {
        List<Member> member = memberRepository.findByOrderByMbnumDesc();
        return member;
    }
    public List<Member> findByOrderByDeptAscMblevelAsc() {
        List<Member> member = memberRepository.findByOrderByDeptAscMblevelAsc();
        return member;
    }

    public Member findByMbnum(Integer mbnum){
        return memberRepository.findByMbnum(mbnum);
    }
}
