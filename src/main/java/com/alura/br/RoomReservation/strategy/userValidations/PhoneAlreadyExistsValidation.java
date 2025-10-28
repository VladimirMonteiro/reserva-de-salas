package com.alura.br.RoomReservation.strategy.userValidations;

import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
public class PhoneAlreadyExistsValidation implements UserValidationsStategy {

    @Override
    public void validate (User user, UserDto userDto) {
        if (user.getPhone().equals(userDto.phone())) {
            throw new UserAlreadyExistsException("Telefone já cadastrado.");
        }
    }
}
