package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import com.maskman97a.cg_quiz.service.QuestionCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question-categories")
public class QuestionCategoryController extends BaseController {

    @Autowired
    private QuestionCategoryService questionCategoryService;

    @GetMapping()
    public String listCategories(HttpServletRequest req, Model model) {
        model.addAttribute("category", questionCategoryService.getAllCategories(0, 20));
        return renderPage(req, model, "question_category", "list");
    }

    @GetMapping("/add")
    public String showAddCategoryForm(HttpServletRequest req, Model model) {
        return renderPage(req, model, "question_category", "add");
    }

    @PostMapping("/add")
    public String addCategory(HttpServletRequest req, QuestionCategoryEntity category, Model model) {
        if (questionCategoryService.isCategoryNameExists(category.getName())) {
            model.addAttribute("error", "Category name already exists!");
            return renderPage(req, model, "question_category", "add");
        }
        category.setCreatedBy("admin"); // Thay đổi để xác định người dùng hiện tại
        questionCategoryService.saveCategory(category);
        return "redirect:/question-categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(HttpServletRequest req, @PathVariable Long id, Model model) {
        QuestionCategoryEntity category = questionCategoryService.findCategoryById(id);
        if (category == null || !category.getCreatedBy().equals("admin")) { // Kiểm tra quyền chỉnh sửa
            model.addAttribute("error", "You can only edit categories you created!");
            return renderPage(req, model, "question_category", "list");
        }
        model.addAttribute("category", category);
        return renderPage(req, model, "question_category", "edit");
    }

    @PostMapping("/edit")
    public String editCategory(HttpServletRequest req, QuestionCategoryEntity category, Model model) {
        if (questionCategoryService.isCategoryNameExists(category.getName())) {
            model.addAttribute("error", "Category name already exists!");
            return renderPage(req, model, "question_category", "edit");
        }
        questionCategoryService.updateCategory(category);
        return "redirect:/question-categories";
    }

    @GetMapping("/{id}")
    public String deleteCategory(HttpServletRequest req, @PathVariable Long id, Model model) {
        if (questionCategoryService.canDeleteCategory(id)) {
            questionCategoryService.deleteCategory(id);
        } else {
            model.addAttribute("error", "Cannot delete category with existing questions!");
        }
        return "redirect:/question-categories";
    }
}
