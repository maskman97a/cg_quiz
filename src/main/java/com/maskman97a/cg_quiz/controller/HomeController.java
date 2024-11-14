package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {
    @GetMapping
    public String getHomePage(HttpServletRequest httpServletRequest, Model model) {
        renderPage(httpServletRequest, model, "home", null);
        return homePage();
    }
    @GetMapping("/home")
    public ModelAndView home (Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("home");
        String accountTypeMessage = "Đây là tài khoản loại: ";

//        // Lấy loại tài khoản từ thông tin xác thực
//        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserTypeEnum.ADMIN.name()))) {
//            accountTypeMessage += UserTypeEnum.ADMIN.getName();
//        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserTypeEnum.TEACHER.name()))) {
//            accountTypeMessage += UserTypeEnum.TEACHER.getName();
//        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserTypeEnum.STUDENT.name()))) {
//            accountTypeMessage += UserTypeEnum.STUDENT.getName();
//        } else {
//            accountTypeMessage += "Không xác định";
//        }

        modelAndView.addObject("accountTypeMessage", accountTypeMessage);
        return modelAndView;
    }
}
