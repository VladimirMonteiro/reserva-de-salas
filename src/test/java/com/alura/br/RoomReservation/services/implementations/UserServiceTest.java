package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private CreateUserRequestDto createUserRequestDto;

    @BeforeEach
    void setUp() {
        user = buildUser();
        createUserRequestDto = buildCreateUserRequestDto();
    }

    @Test
    void shouldFindUserByIdIfUserExists() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var result = userService.findById(user.getId());

        assertNotNull(result);
        assertEquals(user.getName(), result.name());
        assertEquals(user.getCpf(), result.cpf());
        assertEquals(user.getAge(), result.age());
        assertEquals(user.getPhone(), result.phone());
        assertEquals(user.getEmail(), result.email());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldThrowsObjectNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(ObjectNotFoundException.class,
                () -> userService.findById(anyLong()));
        
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    private User buildUser() {
        return new User(1L, "Alfredo", "111.111.111-99", 30, "(11) 99999-9999", "test@email.com", null);
    }

    private CreateUserRequestDto buildCreateUserRequestDto() {
        return new CreateUserRequestDto(
                "Alfredo",
                "111.111.111-99",
                30,
                "test@email.com",
                "(11) 99999-9999");
    }
}