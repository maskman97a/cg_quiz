package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.TypeExamEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Getter
@Setter
    public class ExamDTO implements Validator {
    private Integer id;
    @NotBlank(message = "ten bao thi lhong dc de trong")
    private String name;
    @NotNull(message = "so luong cau hoi khong dc de trong")
    @Min(value = 1, message = "so luong cau hoi phai lon hon 1")
    private Integer totalQuestion;
    @NotNull(message = "Thoi gian lam bai khong dc de trong")
    @Min(value = 1, message = "Thoi gian lam bai phai lon hon 1")
    private Integer totalTime;
    @NotNull(message = "Loai de thi phai chon")
    private TypeExamEnum type;

    @NotNull(message = "Diem thi khong dc de trong")
    @Min(value = 10, message = "Diem thi phai lon hon 10")
    private Integer graduateMark;

    public ExamDTO() {
    }

    public ExamDTO(Integer id, String name, Integer totalQuestion, Integer totalTime, TypeExamEnum type, Integer graduateMark) {
        this.id = id;
        this.name = name;
        this.totalQuestion = totalQuestion;
        this.totalTime = totalTime;
        this.type = type;
        this.graduateMark = graduateMark;
    }



    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExamDTO examDTO = (ExamDTO) target;
    }
}
