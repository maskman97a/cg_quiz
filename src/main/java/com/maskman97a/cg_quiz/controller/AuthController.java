package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.request.RegisterRequest;
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
    public String register(HttpServletRequest httpServletRequest, @RequestParam(name = "fullName") String fullName, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        try {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail(email);
            registerRequest.setFullName(fullName);
            registerRequest.setPassword(password);
            authService.register(httpServletRequest, registerRequest);
        } catch (ValidateException ex) {
            httpServletRequest.setAttribute("email", email);
            httpServletRequest.setAttribute("fullName", fullName);
            httpServletRequest.setAttribute("errorMsg", ex.getMessage());
            return renderPage(httpServletRequest, "auth", "register");
        }
        httpServletRequest.setAttribute("registerMsg", "Đăng ký tài khoản thành công!");
        return renderPage(httpServletRequest, "auth", "login");
    }

    @GetMapping("/forget")
    public String forget(HttpServletRequest httpServletRequest) {
        return renderPage(httpServletRequest, "auth", "forget");
    }

    @PostMapping("/forget")
    public String forget(HttpServletRequest httpServletRequest, Model model, @RequestParam("email") String email) {
        try {
            String otpKey = authService.forget(email);
            model.addAttribute("forgetPasswordSuccessMsg", "Hệ thống đã gửi Email đến " + email);
            model.addAttribute("otpKey", otpKey);
        } catch (ValidateException ex) {
            model.addAttribute("forgetPasswordErrorMsg", "Lỗi hệ thống");
        } catch (Exception ex) {
            model.addAttribute("forgetPasswordMsg", "Lỗi hệ thống");
        }
        return renderPage(httpServletRequest, "auth", "forget-otp");
    }

    @PostMapping("/forget-otp")
    public String forget(HttpServletRequest httpServletRequest, Model model, @RequestParam("otpKey") String otpKey, @RequestParam("otp") String otp, @RequestParam("newPassword") String newPassword) {
        try {
            authService.forgetOtp(otpKey, otp, newPassword);
            model.addAttribute("forgetPasswordSuccessMsg", "Đổi mật khẩu thành công!");
        } catch (ValidateException ex) {
            model.addAttribute("forgetPasswordErrorMsg", ex.getMessage());
        }
        return renderPage(httpServletRequest, "auth", "forget-otp");
    }
}
