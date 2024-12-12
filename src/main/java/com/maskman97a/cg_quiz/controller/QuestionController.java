package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.QuestionDTO;
import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.service.QuestionCategoryService;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/questions")
public class QuestionController extends BaseController {


    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionCategoryService questionCategoryService;

    /**
     * Hiển thị danh sách câu hỏi với phân trang
     */
    @GetMapping
    public String listQuestions(HttpServletRequest req, Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<QuestionDTO> questionPage = questionService.getQuestions(pageable);

        int totalPages = questionPage.getTotalPages();

        // Điều chỉnh page nếu vượt quá tổng số trang
        if (page < 0) page = 0; // Đảm bảo page không âm
        if (page >= totalPages && totalPages > 0) {
            page = totalPages - 1; // Đảm bảo page không vượt quá tổng số trang
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            questionPage = questionService.getQuestions(pageable);
        }

        // Gán các thuộc tính vào Model để sử dụng trong view
        model.addAttribute("questionPage", questionPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // Gán danh sách các số trang (page numbers) để hiển thị
        model.addAttribute("pageNumbers", totalPages > 0
                ? IntStream.range(0, totalPages).boxed().collect(Collectors.toList())
                : List.of());

        return renderPage(req, model, "question", "list");
    }



    /**
     * Xem chi tiết một câu hỏi
     */
    @GetMapping("/{id}")
    public String viewQuestion(HttpServletRequest req, @PathVariable Long id, Model model) {
        try {
            QuestionDTO question = questionService.getQuestionById(id);
            model.addAttribute("question", question);
            return renderPage(req, model, "question", "details");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
    }

    /**
     * Tạo mới câu hỏi - Form
     */
    @GetMapping("/create")
    public String createQuestionForm(HttpServletRequest req, Model model) {
        model.addAttribute("question", new QuestionDTO());
        return renderPage(req, model, "question", "create");
    }

    /**
     * Tạo mới câu hỏi - Xử lý
     */
    @PostMapping("/create")
    public String createQuestion(@ModelAttribute QuestionDTO questionDTO, Model model) {
        try {
            questionService.createQuestion(questionDTO);
            return "redirect:/questions";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error/500";
        }
    }

    /**
     * Chỉnh sửa câu hỏi - Form
     */
    @GetMapping("/edit/{id}")
    public String editQuestionForm(HttpServletRequest req, @PathVariable Long id, Model model) {
        try {
            QuestionDTO question = questionService.getQuestionById(id);
            model.addAttribute("question", question);
            model.addAttribute("categories", questionCategoryService.getAllCategories(1, 9999).getContent());
            return renderPage(req, model, "question", "edit");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
    }

    /**
     * Chỉnh sửa câu hỏi - Xử lý
     */
    @PostMapping("/edit/{id}")
    public String updateQuestion(@PathVariable Long id, @ModelAttribute QuestionDTO questionDTO, Model model) {
        try {
            questionService.updateQuestion(id, questionDTO);
            return "redirect:/questions";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error/500";
        }
    }

    /**
     * Tìm kiếm câu hỏi
     */
    @GetMapping("/search")
    public String searchQuestions(HttpServletRequest req,
                                  @RequestParam("keyword") String keyword,
                                  @RequestParam(value = "difficult", required = false) QuestionDifficultEnum difficult,
                                  Model model) {
        try {
            List<QuestionDTO> questions = questionService.searchQuestions(keyword, difficult);
            model.addAttribute("questions", questions);
            model.addAttribute("keyword", keyword);
            return renderPage(req, model, "question", "list");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error/500";
        }
    }

    /**
     * Xóa câu hỏi
     */
    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, Model model) {
        try {
            questionService.deleteQuestion(id);
            return "redirect:/questions";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error/500";
        }
    }
}
