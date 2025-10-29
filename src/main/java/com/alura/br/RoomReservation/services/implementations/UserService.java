package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.IUserService;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
import com.alura.br.RoomReservation.strategy.userValidations.UserValidationsStategy;
import com.alura.br.RoomReservation.utils.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final List<UserValidationsStategy> validations;

    public UserService(UserRepository userRepository, List<UserValidationsStategy> validations) {
        this.userRepository = userRepository;
        this.validations = validations;
    }

    @Override
    public UserDto createUser(CreateUserRequestDto dto) {
        User newUser = UserMapper.toEntity(dto);

        var userIfExists = userRepository.findByCpfOrPhoneOrEmail(dto.cpf(), dto.phone(), dto.email());

        if (userIfExists.isPresent()) {
            validations.forEach(v -> v.validate(newUser, UserMapper.toDto(userIfExists.get())));
        }

        var savedUser = userRepository.save(newUser);
        return UserMapper.toDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
        return UserMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.getContent().stream().map(UserMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ObjectNotFoundException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);
    }

}
