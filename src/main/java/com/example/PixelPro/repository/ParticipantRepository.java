package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    List<Participant> findAllByMbnum(int mbnum);

    Participant findByMbnumAndCnum(int mbnum, int cnum);

    List<Participant> findAllByCnum(int cnum);

}
