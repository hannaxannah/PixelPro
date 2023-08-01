package com.example.PixelPro.service;

import com.example.PixelPro.entity.Member;
import com.example.PixelPro.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
