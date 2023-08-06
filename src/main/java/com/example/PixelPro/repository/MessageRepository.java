package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "select * from message where mnum = (select max(mnum) from message where cnum = :cnum)", nativeQuery = true)
    Message findRecentMessage(@Param("cnum") int cnum);

    List<Message> findAllByCnumOrderByMtimeAsc(int cnum);

    List<Message> findAllByCnum(int cnum);

    List<Message> findAllByCnumOrderByCnumAsc(int cnum);

    List<Message> findAllByCnumOrderByMnumAsc(int cnum);
    @Modifying
    @Transactional
    @Query(value = "update message set mcontent = '메시지가 삭제 되었습니다.' where mnum = :mnum", nativeQuery = true)
    void deleteMessage(@Param("mnum") int mnum);

    Message findByMnum(int mnum);
}
