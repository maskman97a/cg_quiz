package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.AnswerDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String title;
    private String type; // Kiểu dạng String
    private String difficulty; // Dạng String
    private String categoryName; // Chỉ hiển thị tên danh mục
    private List<AnswerDTO> answers;
}
