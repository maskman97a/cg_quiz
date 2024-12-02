package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.ExamDTO;
import com.maskman97a.cg_quiz.dto.enums.TypeExamEnum;
import com.maskman97a.cg_quiz.entity.ExamEntity;
import com.maskman97a.cg_quiz.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/exam")
@Controller
public class ExamController {
    @Autowired
    private ExamService examService;
    @GetMapping("")
    public String getExamPage(){
        return "exam/exam";
    }
    @GetMapping("/list")
    public String getExamListPage(Model model){
        List<ExamEntity> examList = examService.getList();
        model.addAttribute("examList", examList);
        return "exam/exam";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("exam", new ExamEntity());
        return "exam/create";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("exam") ExamDTO examDTO, BindingResult bindingResult, Model model){
        new ExamDTO().validate(examDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "exam/create";
        }
        ExamEntity exam = new ExamEntity();
        BeanUtils.copyProperties(examDTO, exam);
        examService.create(exam);
        return "redirect:/exam/list";
    }
}
