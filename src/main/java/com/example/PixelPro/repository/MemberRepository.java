package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByEmail(String email);

    Member findByEmail(String email);

    int countByEmail(String email);

    Member findByMbnum(int mbnum);

    /*주영 - select 작업*/
    List<Member> findByOrderByMbnumDesc();
}
