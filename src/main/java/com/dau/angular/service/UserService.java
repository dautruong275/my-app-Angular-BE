package com.dau.angular.service;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.entity.User;
import com.dau.angular.mapper.UserMapper;
import com.dau.angular.repository.UserRepository;
import com.dau.angular.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserResponseDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toResponseDTO(savedUser);
    }
}
