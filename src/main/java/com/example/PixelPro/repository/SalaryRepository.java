package com.example.PixelPro.repository;

import com.example.PixelPro.entity.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface SalaryRepository extends JpaRepository<SalaryEntity, Integer> {

    List<SalaryEntity> findByOrderBySnumDesc();

    SalaryEntity findBySnum(int snum);

    @Query(value="SELECT stitle, pdate, sum(actsalary) as actsalary, count(snum) as snum from salary group by stitle, pdate", nativeQuery = true)
    List<Map<String, Long>> getSummarySalaryUsers();
}
