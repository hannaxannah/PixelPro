package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByEmail(String email);

    Member findByEmail(String email);

    int countByEmail(String email);

    Member findByMbnum(int mbnum);

    /*주영 - select 작업*/
    List<Member> findByOrderByMbnumDesc();

    List<Member> findByOrderByDeptAscMblevelAsc();

    @Query(value = "select * from member where mbnum != :mbnum", nativeQuery = true)
    List<Member> findAllByNotMbnum(@RequestParam("mbnum") int mbnum);

    @Query(value = "select * from member where mbnum NOT IN :mbnums", nativeQuery = true)
    List<Member> findAllByNotMbnums(@Param("mbnums") List<Integer> mbnums);
}
