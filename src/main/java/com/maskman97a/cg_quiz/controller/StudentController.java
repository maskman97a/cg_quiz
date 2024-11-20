package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.request.UserDetailDto;
import com.maskman97a.cg_quiz.entity.StudentEntity;
import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.repository.IStudentRepository;
import com.maskman97a.cg_quiz.repository.UserRepository;
import com.maskman97a.cg_quiz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/students")
public class StudentController extends BaseController{
    @Autowired
    private AdminService adminService;
    private IStudentRepository studentRepository;
@PreAuthorize(value = "adminService")
    public void getAllStudentAccount(Model model){
        UserDetailDto userDetailDto = authentication(model);
        if (userDetailDto != null & userDetailDto.hasRole("ADMIN")){
            adminService.getListStudentAccount();
        }


    }
    @GetMapping("/")
    public String getStudents(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String keyword
    ) {
        Page<StudentEntity> studentPage;
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
        return "listStudent";
    }
}
