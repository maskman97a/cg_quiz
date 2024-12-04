package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.entity.ExamDetailEntity;
import com.maskman97a.cg_quiz.repository.ExamDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamDetailService {
    @Autowired
    private ExamDetailRepository examDetailRepository;

    public List<ExamDetailEntity> getAllExamDetails() {
        return examDetailRepository.findAll();
    }

}
