package com.example.PixelPro.service;

import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Attendance;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public void save(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    public List<Attendance> findByMbnum(int mbnum) {
        return attendanceRepository.findByMbnum(mbnum);
    }

    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    public List<Object[]> countsByMbnumAndAtcategory(int mbnum) {
        return attendanceRepository.countsByMbnumAndAtcategory(mbnum);
    }
}
