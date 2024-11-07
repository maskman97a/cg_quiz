package com.maskman97a.cg_quiz.dto.request;

import lombok.Data;

@Data
public class BaseRequest<T> {
    private T data;
}
