package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.dto.ExamDTO;
import com.maskman97a.cg_quiz.dto.enums.TypeExamEnum;
import com.maskman97a.cg_quiz.entity.ExamEntity;
import com.maskman97a.cg_quiz.repository.ExamRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    public List<ExamEntity> getList(){
        return examRepository.findAll();
    }

    public void create(ExamEntity exam) {
        examRepository.save(exam);
    }

    public boolean canDeleteExam(Long id) {
        return true;
    }

    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

    public Page<ExamEntity> getExamList(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    public ExamEntity findById(Long id) {
        return examRepository.findById(id).orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    public void updateExam(ExamEntity exam) {
        examRepository.save(exam);
    }

    public Page<ExamEntity> searchExamsByName(String searchQuery, Pageable pageable) {
        return examRepository.findByNameContainingIgnoreCase(searchQuery, pageable);
    }
}
