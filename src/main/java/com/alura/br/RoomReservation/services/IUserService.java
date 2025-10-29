package com.alura.br.RoomReservation.services;

import java.util.List;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;

public interface IUserService {

    UserDto createUser(CreateUserRequestDto dto);
    UserDto findById(Long id);
    List<UserDto> findAll(int page, int size);
    void deleteUser(Long id);
}
