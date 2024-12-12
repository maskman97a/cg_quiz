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

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) throws PermissionException {
        String accountTypeMessage = "Đây là tài khoản loại: ";
        // Lấy loại tài khoản từ thông tin xác thực
        UserDetailDto userDetailDto = authentication(model, "STUDENT");
        accountTypeMessage += UserTypeEnum.valueOf(userDetailDto.getRole()).name();
        model.addAttribute("accountTypeMessage", accountTypeMessage);

        return renderPage(request, model, null, null);
    }
}
