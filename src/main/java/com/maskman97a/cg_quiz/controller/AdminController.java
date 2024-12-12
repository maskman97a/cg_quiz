package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.UserDetailDto;
import com.maskman97a.cg_quiz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private AdminService adminService;

    public void getAllStudentAccount(Model model) {
        UserDetailDto userDetailDto = authentication(model);
        if (userDetailDto != null && userDetailDto.hasRole("ADMIN")) {
            adminService.getListStudentAccount();
        }
//        throw Exception("403 - Khong co quyen truy cap")
    }
}
