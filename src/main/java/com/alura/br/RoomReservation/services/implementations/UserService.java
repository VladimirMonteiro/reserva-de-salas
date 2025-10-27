package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.IUserService;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import com.alura.br.RoomReservation.utils.UserMapper;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(CreateUserRequestDto dto) {
        var newUser = UserMapper.toEntity(dto);

        try {
            var savedUser = userRepository.save(newUser);
            return UserMapper.toDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            String msg = e.getMostSpecificCause().getMessage();

            if (msg.contains("users_cpf_key")) {
                throw new UserAlreadyExistsException("CPF já cadastrado.");
            } else if (msg.contains("users_email_key")) {
                throw new UserAlreadyExistsException("Email já cadastrado.");
            } else if (msg.contains("users_phone_key")) {
                throw new UserAlreadyExistsException("Telefone já cadastrado.");
            } else {
                throw e;
            }
        }
    }
}
