package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.entity.TeacherEntity;
import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.repository.TeacherRepository;
import com.maskman97a.cg_quiz.repository.UserRepository;
import com.maskman97a.cg_quiz.service.AdminService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teachers")
public class TeacherController extends BaseController{
//    @Autowired
//    private AdminService adminService;
//    private TeacherRepository teacherRepository;
//    @GetMapping("/teachers")
//    public String getTeacher(
//            Model model,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "") String keyword
//    ) {
//        Page<TeacherEntity> teacherPage;
//        if (keyword.isEmpty()) {
//            teacherPage = teacherRepository.findAll(PageRequest.of(page, size));
//        } else {
//            teacherPage = teacherRepository.findByNameContaining(keyword, PageRequest.of(page, size));
//        }
//
//        model.addAttribute("teachers", teacherPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", teacherPage.getTotalPages());
//        model.addAttribute("totalItems", teacherPage.getTotalElements());
//        model.addAttribute("keyword", keyword);
//
//        return "listTeacher";
//    }

}
