package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert,Integer> {

    List<Alert> findByMbnumOrderByAlnumDesc(int mbnum);

    Alert findByAlnum(int alnum);
}
