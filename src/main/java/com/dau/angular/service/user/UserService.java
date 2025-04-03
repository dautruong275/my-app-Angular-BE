package com.dau.angular.service.user;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.entity.User;
import com.dau.angular.mapper.UserMapper;
import com.dau.angular.repository.UserRepository;
import com.dau.angular.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor// Lombok annotation để tự động tạo constructor với các final fields
public class UserService implements  IUserService{
    private final UserRepository userRepository;
    public UserResponseDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) == null) {
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

    @Override
    public Page<UserResponseDTO> searchUsers(String keyword, PageRequest pageRequest) {
        // Triển khai tìm kiếm (đã có từ trước)
        Page<User> userPage = userRepository.findByKeyword(keyword, pageRequest);
        return userPage.map(UserMapper.INSTANCE::toResponseDTO);
    }
}
