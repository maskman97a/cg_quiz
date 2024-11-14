package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

    public String adminPage() {
        return "admin/admin";
    }

    public String renderPage(HttpServletRequest httpServletRequest, Model model, String tabName, String functionName) {
        if (!DataUtils.isNullObject(model.getAttribute("role"))
                && model.getAttribute("role") == "ADMIN") {
            return renderPage(httpServletRequest, tabName, functionName, "admin");
        } else {
            return renderPage(httpServletRequest, "home", null);
        }
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
        if (!DataUtils.isNullObject(roleName)
                && DataUtils.safeEqual("admin", roleName)) {
            return adminPage();
        } else {
            return homePage();
        }
    }

    protected void authentication(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng có đăng nhập không
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy đối tượng UserDetails
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                String username = userDetails.getUsername();
                String roleName = "";
                model.addAttribute("fullName", username);
                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    roleName = authority.getAuthority();
                }
                model.addAttribute("role", roleName);
            } else {
                model.addAttribute("fullName", null);
            }
        }
    }
}
