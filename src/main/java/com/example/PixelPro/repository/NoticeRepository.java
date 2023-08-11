package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {


    @Modifying
    List<Notice> findByOrderByNnumDesc();

    Notice findByNnum(int nnum);
    @Modifying
    List<Notice> findByOrderByNimportantDescNdateDesc();

    List<Notice> findTop10ByOrderByNimportantDescNdateDesc();


}
