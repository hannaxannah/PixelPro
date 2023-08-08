package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CommuteRepository extends JpaRepository<Commute, Integer> {
    List<Commute> findByMbnum(int mbnum);

    Commute findTop1ByMbnumOrderByCmnumDesc(int mbnum);
}
