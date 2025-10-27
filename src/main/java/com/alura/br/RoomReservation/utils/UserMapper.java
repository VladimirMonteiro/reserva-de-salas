package com.alura.br.RoomReservation.utils;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;

public class UserMapper {

    public static User toEntity(CreateUserRequestDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setCpf(dto.cpf());
        user.setAge(dto.age());
        user.setPhone(dto.phone());
        user.setEmail(dto.email());
        return user;
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getName(),
                user.getCpf(),
                user.getAge(),
                user.getPhone(),
                user.getEmail()
        );
    }
}
