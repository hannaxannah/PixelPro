package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    List<Club> findByOrderByClnumDesc();

    Club findByCltitle(String cltitle);
}
