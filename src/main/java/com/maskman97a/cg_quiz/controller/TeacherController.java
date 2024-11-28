package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.entity.TeacherEntity;
import com.maskman97a.cg_quiz.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("")
    public String getTeacherPage(HttpServletRequest httpServletRequest, Model model) {
        return getTeacherListPage(httpServletRequest, model);
    }

    @GetMapping("/list")
    public String getTeacherListPage(HttpServletRequest httpServletRequest, Model model) {
        List<TeacherEntity> teacherList = teacherService.getList();
        model.addAttribute("teacherList", teacherList);
        return renderPage(httpServletRequest, model, "teacher", "list");
    }

    @GetMapping("/create")
    public String getCreateTeacherPage(HttpServletRequest httpServletRequest, Model model) {
        return renderPage(httpServletRequest, model, "teacher", "create");
    }

    @PostMapping("/create")
    public String createTeacher(HttpServletRequest httpServletRequest, Model model, @RequestParam(name = "name") String teacherName, @RequestParam(name = "age") Integer teacherAge) {
        teacherService.createTeacher(teacherName, teacherAge);
        return getTeacherListPage(httpServletRequest, model);
    }

    @GetMapping("/edit")
    public String getEditTeachetPage(HttpServletRequest httpServletRequest, Model model, @RequestParam(name = "id") Long id) {
        TeacherEntity teacherEntity = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacherEntity);
        return renderPage(httpServletRequest, model, "teacher", "update");
    }

    @PostMapping("/edit")
    public String updateTeacher(HttpServletRequest httpServletRequest, Model model, @RequestParam(name = "id") Long id, @RequestParam(name = "name") String teacherName, @RequestParam(name = "age") Integer teacherAge) {
        teacherService.updateTeacher(id, teacherName, teacherAge);
        return getTeacherListPage(httpServletRequest, model);
    }

    @RequestMapping("/delete")
    public String deleteTeacher(HttpServletRequest httpServletRequest, Model model, @RequestParam(name = "id") Long id) {
        teacherService.deleteTeacher(id);
        return getTeacherListPage(httpServletRequest, model);
    }
}
