package com.maskman97a.cg_quiz.config;

import com.maskman97a.cg_quiz.common.ErrorType;
import com.maskman97a.cg_quiz.dto.response.BaseResponse;
import com.maskman97a.cg_quiz.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleGlobalException(Exception ex, WebRequest request) {
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.setError(ErrorType.SYSTEM_ERROR);
        baseResponse.setDescription(ex.getMessage());
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(baseResponse);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<BaseResponse<?>> handleValidateException(Exception ex, WebRequest request) {
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.setError(ErrorType.INVALID_REQUEST);
        baseResponse.setDescription(ex.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(baseResponse);
    }
}
