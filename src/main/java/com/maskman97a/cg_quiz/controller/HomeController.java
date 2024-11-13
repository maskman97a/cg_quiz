package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {
    @GetMapping
    public String getHomePage(HttpServletRequest httpServletRequest, Model model) {
        authentication(model);
        if (!DataUtils.isNullObject(model.getAttribute("role"))
                && model.getAttribute("role") == "ADMIN") {
            model.addAttribute("roleName","admin");
            return renderPage(httpServletRequest, model, "admin", null);
        } else {
            return renderPage(httpServletRequest, model, "home", null);
        }
    }
}
