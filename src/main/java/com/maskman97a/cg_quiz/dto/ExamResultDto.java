package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.GraduateEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionTypeEnum;
import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import com.maskman97a.cg_quiz.utils.DataUtils;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class ExamResultDto {
    private Long id;
    private Long examId;
    private Long userId;
    private Integer mark;
    private Enum graduate;
    private LocalDateTime expiredDate;
    private LocalDateTime submitTime;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Integer isDeleted;
    private String submitTimeStr;
    private String graduateStr;


    public String getSubmitTimeStr() {
        if (!DataUtils.isNullObject(submitTime))
            submitTimeStr = submitTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return submitTimeStr;
    }

    public String getGraduateStr() {
        if (!DataUtils.isNullObject(mark))
            graduateStr = mark > 80 ? "Đạt yêu cầu" : "Không đạt";
        return graduateStr;
    }


}
