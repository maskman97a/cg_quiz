package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.dto.ExamDTO;
import com.maskman97a.cg_quiz.dto.enums.TypeExamEnum;
import com.maskman97a.cg_quiz.entity.ExamEntity;
import com.maskman97a.cg_quiz.repository.ExamRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void updateExam(ExamEntity exam) {
        examRepository.save(exam);
    }
}
