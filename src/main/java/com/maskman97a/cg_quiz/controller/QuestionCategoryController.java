package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import com.maskman97a.cg_quiz.service.QuestionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionCategoryController {

    @Autowired
    private QuestionCategoryService questionCategoryService;

    @GetMapping("/question-categories")
    public String listCategories(Model model) {
        model.addAttribute("category", questionCategoryService.getAllCategories(0, 20));
        return "category/list";
    }

    @GetMapping("/question-categories/add")
    public String showAddCategoryForm() {
        return "category/add";
    }

    @PostMapping("/question-categories/add")
    public String addCategory(QuestionCategoryEntity category, Model model) {
        if (questionCategoryService.isCategoryNameExists(category.getName())) {
            model.addAttribute("error", "Category name already exists!");
            return "category/add";
        }
        category.setCreatedBy("admin"); // Thay đổi để xác định người dùng hiện tại
        questionCategoryService.saveCategory(category);
        return "redirect:/question-categories";
    }

    @GetMapping("/question-categories/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        QuestionCategoryEntity category = questionCategoryService.findCategoryById(id);
        if (category == null || !category.getCreatedBy().equals("admin")) { // Kiểm tra quyền chỉnh sửa
            model.addAttribute("error", "You can only edit categories you created!");
            return "category/list";
        }
        model.addAttribute("category", category);
        return "category/edit";
    }

    @PostMapping("/question-categories/edit")
    public String editCategory(QuestionCategoryEntity category, Model model) {
        if (questionCategoryService.isCategoryNameExists(category.getName())) {
            model.addAttribute("error", "Category name already exists!");
            return "category/edit";
        }
        questionCategoryService.updateCategory(category);
        return "redirect:/question-categories";
    }

    @GetMapping("/question-categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        if (questionCategoryService.canDeleteCategory(id)) {
            questionCategoryService.deleteCategory(id);
        } else {
            model.addAttribute("error", "Cannot delete category with existing questions!");
        }
        return "redirect:/question-categories";
    }
}
