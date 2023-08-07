package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Gapproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtapprovalRepository extends JpaRepository<Atapproval, Integer> {
    List<Atapproval> findByAtwmbnumOrderByAtnumDesc(int mbnum);

    List<Atapproval> findByAthmbnumOrderByAtnumDesc(int mbnum);
}
