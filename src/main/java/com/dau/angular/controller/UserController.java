package com.dau.angular.controller;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.response.BaseResponse;
import com.dau.angular.response.UserResponseDTO;
import com.dau.angular.service.UserService;
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
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserResponseDTO savedUser = userService.registerUser(userDTO);

        BaseResponse<UserResponseDTO> response = BaseResponse.<UserResponseDTO>builder()
                .status(HttpStatus.CREATED) // Trả về mã số 201 thay vì HttpStatus trực tiếp
                .message("User registered successfully")
                .data(savedUser)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<BaseResponse<List<UserResponseDTO>>> getAllUsers() {
        logger.info("Fetching all users...");
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Users retrieved", users));
    }
}
