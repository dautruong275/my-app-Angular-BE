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
    private HttpStatus status; // Đổi từ HttpStatus -> int

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;
}
