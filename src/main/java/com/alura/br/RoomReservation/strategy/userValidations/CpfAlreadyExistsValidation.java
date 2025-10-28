package com.alura.br.RoomReservation.strategy.userValidations;

import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
public class CpfAlreadyExistsValidation implements UserValidationsStategy{

    @Override
    public void validate (User user, UserDto userDto) {
        if (user.getCpf().equals(userDto.cpf())) {
            throw new UserAlreadyExistsException("CPF já cadastrado.");
        }
    }
}
