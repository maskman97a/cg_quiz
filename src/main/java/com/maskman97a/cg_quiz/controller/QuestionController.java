package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.QuestionDTO;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // List
    @GetMapping("/list")
    public String listQuestions(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("questions", questionService.getQuestions(page, 5));
        return "questionList";
    }

    // Search
    @GetMapping("/search")
    public String searchQuestions(@RequestParam String keyword,
                                  @RequestParam(required = false) String difficult,
                                  @RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("questions", questionService.searchQuestions(keyword, difficult, page));
        return "questionList";
    }

    //  details
    @GetMapping("/details/{id}")
    public String viewQuestionDetails(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionDetails(id));
        return "questionDetails";
    }

    // Add a new question
    @GetMapping("/add")
    public String showAddQuestionForm(Model model) {
        model.addAttribute("question", new QuestionEntity());
        return "addQuestion";
    }

    // Save new question
    @PostMapping("/add")
    public String addQuestion(@ModelAttribute QuestionEntity question) {
        questionService.saveQuestion(question);
        return "redirect:/questions/list";
    }

    // Delete a question
    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/questions/list";
    }

    // Update a question
    @GetMapping("/edit/{id}")
    public String showEditQuestionForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionDetails(id));
        return "editQuestion";
    }

    // Save the updated question
    @PostMapping("/edit/{id}")
    public String updateQuestion(@ModelAttribute QuestionEntity question, @PathVariable Long id) {
        question.setId(id);
        questionService.saveQuestion(question);
        return "redirect:/questions/list";
    }

}
