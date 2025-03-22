package com.dau.angular.service;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserService {
    UserResponseDTO registerUser(UserDTO userDTO) throws Exception;
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();

    Page<UserResponseDTO> searchUsers(String keyword, PageRequest pageRequest);
}
