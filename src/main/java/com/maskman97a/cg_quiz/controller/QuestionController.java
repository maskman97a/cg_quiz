package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.QuestionDTO;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/questions")

public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    // Hiển thị tất cả câu hỏi với phân trang
    @GetMapping
    public String listQuestions(HttpServletRequest req, Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
        Page<QuestionEntity> questionPage = questionService.getQuestions(pageable);
        model.addAttribute("questionPage", questionPage);
        return renderPage(req, model, "question", "list");
    }

    // Xem chi tiết câu hỏi
    @GetMapping("/{id}")
    public String viewQuestion(HttpServletRequest req, @PathVariable Long id, Model model) {
        QuestionDTO question = questionService.getQuestionById(id);
        model.addAttribute("question", question);
        return renderPage(req, model, "question", "details");
    }

    // Tạo mới câu hỏi
    @GetMapping("/create")
    public String createQuestionForm(HttpServletRequest req, Model model) {
        model.addAttribute("questionDTO", new QuestionDTO());
        return renderPage(req, model, "question", "create");
    }

    @PostMapping("/create")
    public String createQuestion(@ModelAttribute QuestionDTO questionDTO) {
        questionService.createQuestion(questionDTO);
        return "redirect:/questions";
    }

    // Cập nhật câu hỏi
    @GetMapping("/edit/{id}")
    public String editQuestionForm(HttpServletRequest req, @PathVariable Long id, Model model) {
        QuestionDTO question = questionService.getQuestionById(id);
        model.addAttribute("question", question);
        return renderPage(req, model, "question", "edit");
    }

    @PostMapping("/edit/{id}")
    public String updateQuestion(@PathVariable Long id, @ModelAttribute QuestionDTO questionDTO) {
        questionService.updateQuestion(id, questionDTO);
        return "redirect:/questions";
    }

    // Tìm kiếm câu hỏi
    @GetMapping("/search")
    public String searchQuestions(HttpServletRequest req, @RequestParam String title, Model model) {
        List<QuestionEntity> questions = questionService.searchQuestions(title);
        model.addAttribute("questions", questions);
        return renderPage(req, model, "question", "search");
    }

    // Xóa câu hỏi
    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/questions";
    }

}
