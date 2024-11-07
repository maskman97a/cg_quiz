package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.DeleteEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto {
    protected Long id;
    protected LocalDateTime createdAt;
    protected String createdBy;
    protected LocalDateTime updatedAt;
    protected String updatedBy;
    protected DeleteEnum isDeleted;
}
