package com.maskman97a.cg_quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maskman97a.cg_quiz.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController extends BaseController {
    private final AdminService adminService;
    private final ObjectMapper jacksonObjectMapper;
    private final static String TAB_NAME = "admin";
    private final static String ROLE_NAME = "admin";

    @GetMapping
    public String adminPage(HttpServletRequest httpServletRequest, Model model) {
//        model.addAttribute("services", serviceService.getAllServices());
        return renderPage(httpServletRequest, TAB_NAME, "admin-register-list", ROLE_NAME);
    }


    @GetMapping("/admin-register-list")
    public String serviceList(HttpServletRequest httpServletRequest, Model model) {
        return renderPage(httpServletRequest, TAB_NAME, "admin-register-list", ROLE_NAME);
    }

    @GetMapping("/create")
    public String serviceCreate(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("service", ""); // Tạo đối tượng rỗng cho form
        return renderPage(httpServletRequest, TAB_NAME, "create", ROLE_NAME);
    }


}
