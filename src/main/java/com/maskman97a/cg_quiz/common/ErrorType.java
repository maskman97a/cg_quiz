package com.maskman97a.cg_quiz.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    SUCCESS(0, "Success", "Thành công"),
    FAIL(1, "Fail", "Thất bại"),
    INVALID_REQUEST(98, "Invalid Request", "Dữ liệu không hợp lệ"),
    SYSTEM_ERROR(99, "System Error", "Lỗi hệ thống");
    final int errorCode;
    final String errorMessage;
    final String description;

}
