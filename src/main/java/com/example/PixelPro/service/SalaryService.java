package com.example.PixelPro.service;

import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;

    public void saveSalary(SalaryEntity salary) {
        salaryRepository.save(salary);
    }

    public List<SalaryEntity> findByOrderBySnumDesc() {
        List<SalaryEntity> salary = salaryRepository.findByOrderBySnumDesc();
        return salary;
    }

    public void deleteAllBySnum(List<Integer> row) {
        salaryRepository.deleteAllById(row);
    }

    public void salaryDelete(int snum) {
        SalaryEntity salary = salaryRepository.findBySnum(snum);
        salaryRepository.delete(salary);
    }

    public SalaryEntity getSalaryBySnum(int snum) {
        SalaryEntity salary = salaryRepository.findBySnum(snum);
        return salary;
    }
}
