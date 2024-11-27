package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.UserDetailDto;
import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import com.maskman97a.cg_quiz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/students")
    public String getAllStudentAccount(Authentication authentication, Model model) {
        UserDetailDto userDetailDto = authentication(model);

        // Kiểm tra nếu không phải ADMIN thì trả về trang lỗi
        if (userDetailDto == null || !userDetailDto.hasRole(UserTypeEnum.ADMIN.name())) {
            return "error/403";
        }

        model.addAttribute("students", adminService.getListStudentAccount());
        return "admin/students";
    }
}
