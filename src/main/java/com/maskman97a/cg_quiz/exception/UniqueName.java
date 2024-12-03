package com.maskman97a.cg_quiz.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueNameValidator.class) // Trỏ đến lớp xử lý logic
@Target({ElementType.FIELD, ElementType.METHOD}) // Áp dụng cho trường hoặc phương thức
@Retention(RetentionPolicy.RUNTIME) // Annotation có hiệu lực tại runtime
public @interface UniqueName {
    String message() default "Tên danh mục đã tồn tại!"; // Thông báo lỗi mặc định
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
