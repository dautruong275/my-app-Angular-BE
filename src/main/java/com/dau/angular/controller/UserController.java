package com.dau.angular.controller;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.response.BaseResponse;
import com.dau.angular.response.ResponseObject;
import com.dau.angular.response.UserResponseDTO;
import com.dau.angular.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@Tag(name = "User API", description = "Quản lý tài khoản người dùng")

public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Operation(summary = "Đăng ký tài khoản", description = "Tạo mới một tài khoản người dùng")
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("Nhận yêu cầu đăng ký user: username={}, email={}",
                userDTO.getUsername(), userDTO.getEmail());
        UserResponseDTO savedUser = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED, "User registered successfully", savedUser));
    }
    @GetMapping
    public ResponseEntity<BaseResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        logger.info("Lấy thành công thông tin user: username={}", users);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Users retrieved", users));
    }
}
