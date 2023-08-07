package com.example.PixelPro.service;

import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Attendance;
import com.example.PixelPro.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public void save(Attendance attendance) {
        attendanceRepository.save(attendance);
    }
}
