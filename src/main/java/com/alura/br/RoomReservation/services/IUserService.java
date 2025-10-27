package com.alura.br.RoomReservation.services;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;

public interface IUserService {

    UserDto createUser(CreateUserRequestDto dto);
}
