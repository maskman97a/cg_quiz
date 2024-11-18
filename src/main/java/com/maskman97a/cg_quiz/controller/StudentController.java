package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.entity.Student;
import com.maskman97a.cg_quiz.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentRepository studentRepository;

    @GetMapping("")
    public String getStudents(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String keyword
    ) {
        Page<Student> studentPage;
        if (keyword.isEmpty()) {
            studentPage = studentRepository.findAll(PageRequest.of(page, size));
        } else {
            studentPage = studentRepository.findByNameContaining(keyword, PageRequest.of(page, size));
        }

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());
        model.addAttribute("keyword", keyword);

        return "auth/listStudent";
    }

}
