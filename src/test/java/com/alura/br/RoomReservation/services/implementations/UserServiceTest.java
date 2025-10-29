package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    @Test
    void shouldReturnListOfUsersWhenUsersExist() {
        List<User> users = List.of(user);
        Page<User> usersPage = new PageImpl<>(users);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(usersPage);

        List<UserDto> result = userService.findAll(0, 20);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getName(), result.get(0).name());
        assertEquals(user.getEmail(), result.get(0).email());
        assertEquals(user.getCpf(), result.get(0).cpf());
        assertEquals(user.getAge(), result.get(0).age());
        assertEquals(user.getPhone(), result.get(0).phone());
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void shouldDeleteUserByIdWhenUserExists() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);
        doNothing().when(userRepository).deleteById(id);

        assertDoesNotThrow(() -> userService.deleteUser(id));

        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowsObjectNotFoundExceptionWhenDeleteUserNotExists() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        var exception = assertThrows(ObjectNotFoundException.class, () -> userService.deleteUser(id));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(userRepository, times(1)).existsById(id);
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