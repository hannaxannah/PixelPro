package com.example.PixelPro.repository;

import com.example.PixelPro.entity.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<SalaryEntity, Integer> {

    List<SalaryEntity> findByOrderBySnumDesc();

    SalaryEntity findBySnum(int snum);

}
