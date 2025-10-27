package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private CreateUserRequestDto createUserRequestDto;

    @BeforeEach
    void setUp () {
        user = buildUser();
        createUserRequestDto = buildCreateUserRequestDto();
    }

    @Test
    void shouldThrowsUserAlreadyExistsExceptionWhenCpfAlreadyExist () {
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

        var exception = assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(createUserRequestDto));

        assertEquals("Usuário já cadastrado.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldCreateUserWhenAllInputsIsValid() {
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var createUser = userService.createUser(createUserRequestDto);

        assertNotNull(createUser);
        assertEquals(user.getCpf(), createUser.cpf());
        assertEquals(user.getName(), createUser.name());
        assertEquals(user.getAge(), createUser.age());
        assertEquals(user.getPhone(), createUser.phone());
        assertEquals(user.getEmail(), createUser.email());

        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findByCpf(createUser.cpf());

    }


    private User buildUser () {
        return new User(1L, "Alfredo", "111.111.111-99", 30, "(11) 99999-9999", "test@email.com", null);
    }

    private CreateUserRequestDto buildCreateUserRequestDto () {
        return new CreateUserRequestDto(
                "Alfredo",
                "111.111.111-99",
                30,
                "(11) 99999-9999",
                "test@email.com");
    }
}