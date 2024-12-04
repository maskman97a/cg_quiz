package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.ExamDTO;
import com.maskman97a.cg_quiz.dto.enums.TypeExamEnum;
import com.maskman97a.cg_quiz.entity.ExamEntity;
import com.maskman97a.cg_quiz.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ExamController {
    @Autowired
    private ExamService examService;
    @GetMapping("/exam")
    public String getExamPage(){
        return "exam/exam";
    }
    @GetMapping("/exam/list")
    public String getExamListPage(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ExamEntity> examPage = examService.getExamList(pageable);

        model.addAttribute("examList", examPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", examPage.getTotalPages());
        model.addAttribute("totalItems", examPage.getTotalElements());
        return "exam/exam";
    }
    @GetMapping("/exam/search")
    public String searchExams(@RequestParam("searchQuery") String searchQuery,
                              @RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        // Tìm kiếm bài thi theo tên
        Page<ExamEntity> examPage = examService.searchExamsByName(searchQuery, pageable);

        model.addAttribute("examList", examPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", examPage.getTotalPages());
        model.addAttribute("totalItems", examPage.getTotalElements());
        model.addAttribute("searchQuery", searchQuery);
        return "exam/exam";
    }
    @GetMapping("/exam/create")
    public String create(Model model){
        model.addAttribute("exam", new ExamEntity());
        return "exam/create";
    }

    @PostMapping("/exam/create")
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
    @GetMapping("/exam/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        ExamEntity exam = examService.findById(id);
        model.addAttribute("exam", exam);
        return "exam/update";
    }

    // Lưu thông tin bài kiểm tra đã sửa
    @PostMapping("/exam/update/{id}")
    public String updateExam(@PathVariable("id") Long id, @ModelAttribute ExamEntity exam) {
        exam.setId(id);
        examService.updateExam(exam);
        return "redirect:/exam/list";
    }
    @GetMapping("/exam/delete/{id}")
    public String deleteExam(@PathVariable Long id, Model model){
        if (examService.canDeleteExam(id)) {
            examService.deleteExam(id);
        } else {
            model.addAttribute("error", "Cannot delete exam with exxisting");
        }
        return "redirect:/exam/list";
    }



}
