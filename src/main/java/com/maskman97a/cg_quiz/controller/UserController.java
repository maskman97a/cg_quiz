package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.UserDetailDto;
import com.maskman97a.cg_quiz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController extends BaseController {
    private final UserService userService;

    @RequestMapping("/view")
    public String view(HttpServletRequest httpServletRequest, Model model) {
         authentication(model);
        return renderPage(httpServletRequest, model, "user", "view");
    }


    @RequestMapping("/update")
    public String update(HttpServletRequest httpServletRequest, Model model, @RequestParam("fullName") String fullName, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        authentication(model);
        userService.update(fullName, avatar);
        model.addAttribute("updateUserMsg", "Cập nhật thông tin người dùng thành công!");
        return renderPage(httpServletRequest, model, "user", "view");
    }
}
