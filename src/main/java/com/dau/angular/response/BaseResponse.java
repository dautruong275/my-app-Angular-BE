package com.dau.angular.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    @JsonProperty("status")
    private int status; // Đổi từ HttpStatus -> int

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public BaseResponse(HttpStatus status, String message, T data) {
        this.status = status.value(); // Lấy mã số của HTTP status (200, 201, 400, ...)
        this.message = message;
        this.data = data;
    }
}
