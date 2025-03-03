package com.dau.angular.service;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.entity.User;
import com.dau.angular.mapper.UserMapper;
import com.dau.angular.repository.UserRepository;
import com.dau.angular.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements  IUserService{
    @Autowired
    private UserRepository userRepository;
    public UserResponseDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }
        // Chuyển DTO thành Entity
        User user = UserMapper.INSTANCE.toEntity(userDTO);
        // Lưu vào database
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::toResponseDTO)
                .collect(Collectors.toList());
    }
}
