package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.IUserService;
import com.alura.br.RoomReservation.strategy.userValidations.UserValidationsStategy;
import com.alura.br.RoomReservation.utils.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final List<UserValidationsStategy> validations = new ArrayList<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(CreateUserRequestDto dto) {
        User newUser = UserMapper.toEntity(dto);

        userRepository.findByCpfOrPhoneOrEmail(dto.cpf(), dto.phone(), dto.email())
                .ifPresent((u) -> validations.forEach(
                        v -> v.validate(u, UserMapper.toDto(newUser))));

        var savedUser = userRepository.save(newUser);
        return UserMapper.toDto(savedUser);
    }
}
