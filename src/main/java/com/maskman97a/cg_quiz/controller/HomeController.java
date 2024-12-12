package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.UserDetailDto;
import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import com.maskman97a.cg_quiz.exception.PermissionException;
import com.maskman97a.cg_quiz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController extends BaseController {

    @GetMapping
    public String getHomePage(HttpServletRequest httpServletRequest, Model model) {
        renderPage(httpServletRequest, model, "home", null);
        return homePage();
    }

//    @GetMapping("/check")
//    public String home(HttpServletRequest request, Model model) throws PermissionException {
//        // Lấy thông tin người dùng từ Authentication
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
//
//        String accountTypeMessage = "Đây là tài khoản loại: ";
//        accountTypeMessage += UserTypeEnum.valueOf(userDetailDto.getRole()).name();
//
//        model.addAttribute("accountTypeMessage", accountTypeMessage);
//
//        // Kiểm tra và điều hướng theo loại tài khoản
//        String role = userDetailDto.getRole();
//        if (UserTypeEnum.ADMIN.name().equals(role)) {
//            return "redirect:/admin"; // Điều hướng đến trang admin
//        } else if (UserTypeEnum.TEACHER.name().equals(role)) {
//            return "redirect:/teacher"; // Điều hướng đến trang teacher
//        } else if (UserTypeEnum.STUDENT.name().equals(role)) {
//            return "redirect:/student"; // Điều hướng đến trang student
//        }
//
//        // Nếu không tìm thấy loại tài khoản hợp lệ, trả về trang lỗi
//        return "error/403";
//    }
}
