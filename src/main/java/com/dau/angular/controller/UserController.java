package com.dau.angular.controller;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.response.BaseResponse;
import com.dau.angular.response.UserResponseDTO;
import com.dau.angular.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserResponseDTO savedUser = userService.registerUser(userDTO);

        BaseResponse<UserResponseDTO> response = BaseResponse.<UserResponseDTO>builder()
                .status(HttpStatus.CREATED.value()) // Trả về mã số 201 thay vì HttpStatus trực tiếp
                .message("User registered successfully")
                .data(savedUser)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
