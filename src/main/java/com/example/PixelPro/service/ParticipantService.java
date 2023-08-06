package com.example.PixelPro.service;

import com.example.PixelPro.entity.Participant;
import com.example.PixelPro.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    @Autowired
    ParticipantRepository participantRepository;

    public List<Participant> getParticipantByMbNum(int mbnum) {
        List<Participant> participantList = participantRepository.findAllByMbnum(mbnum);
        return participantList;
    }

    public Participant getParticipantByMbnumAndCnum(int mbnum, int cnum) {
        Participant participant = participantRepository.findByMbnumAndCnum(mbnum, cnum);
        return participant;
    }

    public List<Participant> getParticipantsByCnum(int cnum) {
        List<Participant> participantList = participantRepository.findAllByCnum(cnum);
        return participantList;
    }

    public void save(Participant participant) {
        participantRepository.save(participant);
    }

    public void deleteParticipant(Participant participant) {
        participantRepository.delete(participant);
    }
}
