package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Gapproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GapprovalRepository extends JpaRepository<Gapproval, Integer> {

    Gapproval findByGanum(Integer ganum);

    List<Gapproval> findByGwmbnumOrderByGanumDesc(int mbnum);
}
