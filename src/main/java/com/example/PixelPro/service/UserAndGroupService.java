package com.example.PixelPro.service;

import com.example.PixelPro.entity.Member;
import com.example.PixelPro.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAndGroupService {
    @Autowired
    MemberRepository memberRepository;

    public int emailCheck(String email) {
        int emailCheck = memberRepository.countByEmail(email);
        return emailCheck;
    }

    public Member getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member;
    }

    public Member getMemberByMbnum(int mbnum) {
        Member member = memberRepository.findByMbnum(mbnum);
        return member;
    }

    public List<Member> getAllMembers() {
        List<Member> memberList = memberRepository.findAll();
        return memberList;
    }

    public List<Member> getAllMembersExcludingLogin(int mbnum) {
        List<Member> memberList = memberRepository.findAllByNotMbnum(mbnum);
        return memberList;
    }

    public List<Member> getAllMembersExcludingConvUsers(List<Integer> mbnums) {
        List<Member> memberList = memberRepository.findAllByNotMbnums(mbnums);
        return memberList;
    }
   /* public List<Map<String,Object>> fetchAll(String myId) {
        //List<Map<String,Object>> getAllUser=jdbcTemplate.queryForList("select * from users where id!=?",myId);

      //  return getAllUser;

    }


    public List<Map<String,Object>> fetchAllGroup(String groupId) {
       // List<Map<String,Object>> getAllUser=jdbcTemplate.queryForList("SELECT gr.* FROM `groups` gr " +
            //    "join group_members gm on gm.group_id=gr.id and gm.user_id=?",groupId);

     //   return getAllUser;
    }*/
}