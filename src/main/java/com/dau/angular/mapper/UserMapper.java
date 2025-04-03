package com.dau.angular.mapper;

import com.dau.angular.dto.UserDTO;
import com.dau.angular.entity.User;
import com.dau.angular.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(UserDTO userDTO);
    UserResponseDTO toResponseDTO(User user);
}
