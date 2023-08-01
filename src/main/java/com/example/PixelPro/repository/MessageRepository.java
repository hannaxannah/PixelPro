package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "select * from message where mnum = (select max(mnum) from message where cnum = :cnum)", nativeQuery = true)
    Message findRecentMessage(@Param("cnum") int cnum);

    List<Message> findAllByCnumOrderByMtimeAsc(int cnum);

    List<Message> findAllByCnum(int cnum);
}
