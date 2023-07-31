package com.example.PixelPro.repository;
import com.example.PixelPro.entity.Msgstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MsgstatusRepository extends JpaRepository<Msgstatus, Integer> {
    @Query(value = "select count(*) from msgstatus where cnum = :cnum and cpnum = :cpnum and isread = 'unread'", nativeQuery = true)
    int countUnread(@Param("cnum") int cnum, @Param("cpnum") int cpnum);
}
