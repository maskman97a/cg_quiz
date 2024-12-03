package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import com.maskman97a.cg_quiz.repository.QuestionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class QuestionCategoryService {

    @Autowired
    private QuestionCategoryRepository repository;

    public void saveCategory(QuestionCategoryEntity category) {
        repository.save(category);
    }

    public boolean isCategoryNameExists(String name) {
        return repository.existsByName(name);
    }

    public Page<QuestionCategoryEntity> getAllCategories(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public QuestionCategoryEntity findCategoryById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void updateCategory(QuestionCategoryEntity category) {
        repository.save(category);
    }

    public boolean canDeleteCategory(Long id) {
        // Logic kiểm tra nếu danh mục có câu hỏi
        // Giả sử bạn có một repository hoặc phương thức để kiểm tra điều này
        return true; // Thay đổi theo logic thực tế của bạn
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
