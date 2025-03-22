package com.dau.angular.controller;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.response.BaseResponse;
import com.dau.angular.response.ResponsePagenable;
import com.dau.angular.response.UserResponseDTO;
import com.dau.angular.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @GetMapping("/get-user-admin")
    public ResponseEntity<?> getAllUsersByAdmin(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        try {
            logger.info("Admin retrieving users with keyword: {}, page: {}, limit: {}", keyword, page, limit);

            PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
            var userPage = userService.searchUsers(keyword.trim(), pageRequest);

            ResponsePagenable<List<UserResponseDTO>> response = new ResponsePagenable<>();
            response.setStatus(HttpStatus.OK);
            response.setMessage("Users retrieved successfully");
            response.setData(userPage.getContent()); // userPage.getContent() trả về List<UserResponseDTO>
            response.setTotalElements(userPage.getTotalElements());
            response.setTotalPages(userPage.getTotalPages());
            response.setCurrentPage(userPage.getNumber());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error retrieving users: {}", e.getMessage());
            ResponsePagenable<List<UserResponseDTO>> errorResponse = new ResponsePagenable<>();
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorResponse.setMessage("Error retrieving users: " + e.getMessage());
            errorResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
