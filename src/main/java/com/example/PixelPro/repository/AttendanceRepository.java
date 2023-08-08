package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Attendance;
import com.example.PixelPro.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByMbnum(int mbnum);

    @Query(value = "SELECT at.atcategory, COUNT(*) as count FROM Attendance at WHERE at.mbnum = :mbnum GROUP BY at.atcategory", nativeQuery = true)
    List<Object[]> countsByMbnumAndAtcategory(@Param("mbnum") int mbnum);

}
