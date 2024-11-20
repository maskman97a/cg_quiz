package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.request.UserDetailDto;
import com.maskman97a.cg_quiz.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BaseController {

    @RequestMapping
    public String redirectHome() {
        return "redirect:/home";
    }

    @RequestMapping("/system_error")
    protected String renderSystemError() {
        return "system_error";
    }


    public String homePage() {
        return "home";
    }

    public String renderPage(HttpServletRequest httpServletRequest, Model model, String tabName, String functionName) {
        authentication(model);
        return renderPage(httpServletRequest, tabName, functionName);
    }

    public String renderPage(HttpServletRequest httpServletRequest, Model model, String tabName, String functionName, String roleName) {
        authentication(model);
        return renderPage(httpServletRequest, tabName, functionName, roleName);
    }

    public String renderPage(HttpServletRequest httpServletRequest, String tabName, String functionName) {
        httpServletRequest.setAttribute("tabName", tabName);
        httpServletRequest.setAttribute("functionName", functionName);
        return homePage();
    }

    public String renderPage(HttpServletRequest httpServletRequest, String tabName, String functionName, String roleName) {
        httpServletRequest.setAttribute("tabName", tabName);
        httpServletRequest.setAttribute("functionName", functionName);
        httpServletRequest.setAttribute("roleName", roleName);
        return homePage();
    }

    protected UserDetailDto authentication(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng có đăng nhập không
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy đối tượng UserDetails
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetailDto userDetails) {
                model.addAttribute("fullName", !DataUtils.isNullOrEmpty(userDetails.getFullName()) ? userDetails.getFullName() : userDetails.getPassword());
                return userDetails;
            } else {
                model.addAttribute("fullName", null);
            }
        }
        return null;
    }
}
