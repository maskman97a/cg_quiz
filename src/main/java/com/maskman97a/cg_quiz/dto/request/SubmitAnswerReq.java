package com.maskman97a.cg_quiz.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Getter
@Setter
public class SubmitAnswerReq {
    private Long examId;

    private Integer questionNo;

    private Set<Long> answerIdList;
}
