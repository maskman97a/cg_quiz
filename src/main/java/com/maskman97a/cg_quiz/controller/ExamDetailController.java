package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.entity.ExamDetailEntity;
import com.maskman97a.cg_quiz.service.ExamDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExamDetailController {
    @Autowired
    private ExamDetailService examDetailService;

    @GetMapping("/exam-details")
    public String getExamDetails(Model model) {
        List<ExamDetailEntity> examDetails = examDetailService.getAllExamDetails();
        model.addAttribute("examDetails", examDetails);
        return "exam/exam-details";
    }
}
