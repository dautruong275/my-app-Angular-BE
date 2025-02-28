package com.dau.angular.service;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.entity.User;
import com.dau.angular.response.UserResponseDTO;

import java.util.List;

public interface IUserService {
    UserResponseDTO registerUser(UserDTO userDTO) throws Exception;
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();

}
