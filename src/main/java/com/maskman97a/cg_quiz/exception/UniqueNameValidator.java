package com.maskman97a.cg_quiz.exception;

import com.maskman97a.cg_quiz.repository.QuestionCategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    @Autowired
    private QuestionCategoryRepository questionCategoryRepository; // Repository kết nối cơ sở dữ liệu

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.trim().isEmpty()) {
            return true; // Nếu null hoặc rỗng, để @NotBlank xử lý
        }
        return !questionCategoryRepository.existsByName(name.trim()); // Trả về false nếu tên đã tồn tại
    }
}
