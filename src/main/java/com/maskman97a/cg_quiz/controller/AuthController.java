package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.exception.ValidateException;
import com.maskman97a.cg_quiz.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest httpServletRequest) {
        return renderPage(httpServletRequest,"auth", "login");
    }


    @GetMapping("/register")
    public String registerPage(HttpServletRequest httpServletRequest) {
        return renderPage(httpServletRequest, "auth", "register");
    }

    @PostMapping("/register")
    public String register(HttpServletRequest httpServletRequest, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        try {
            authService.register(httpServletRequest, username, password);
        } catch (ValidateException ex) {
            httpServletRequest.setAttribute("username", username);
            httpServletRequest.setAttribute("errorMsg", ex.getMessage());
            return renderPage(httpServletRequest, "auth", "register");
        }
        httpServletRequest.setAttribute("registerMsg", "Đăng ký tài khoản thành công!");
        return renderPage(httpServletRequest, "auth", "login");
    }
}
