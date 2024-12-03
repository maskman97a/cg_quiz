package com.maskman97a.cg_quiz.dto.response;

public interface ExamResultDetailDto {
    String getQuestionTitle();

    String getSelectedAnswer();

    String getCorrectAnswers();

    Boolean getIsCorrect();
}
