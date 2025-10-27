package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.IUserService;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import com.alura.br.RoomReservation.utils.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser (CreateUserRequestDto dto) {
        userRepository.findByCpf(dto.cpf())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("Usuário já cadastrado.");
                });

        var newUser = UserMapper.toEntity(dto);
        var userSaved = UserMapper.toDto(newUser);
        userRepository.save(newUser);
        return userSaved;
    }
}
