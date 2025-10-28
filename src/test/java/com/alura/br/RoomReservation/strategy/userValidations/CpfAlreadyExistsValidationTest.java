package com.alura.br.RoomReservation.strategy.userValidations;

import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CpfAlreadyExistsValidationTest {

    private static final String CPF1 = "111.111.111-99";
    private static final String CPF2 = "000.000.000-00";

    @InjectMocks
    private CpfAlreadyExistsValidation validation;

    @Mock
    private User user;

    @Mock
    private UserDto userDto;

    @Test
    void shouldThrowsUserAlreadyExistsExceptionWhenCpfAlreadyExists () {
        when(user.getCpf()).thenReturn(CPF1);
        when(userDto.cpf()).thenReturn(CPF1);

        var exception = Assertions.assertThrows(UserAlreadyExistsException.class,
                ()-> validation.validate(user, userDto));

        assertEquals("CPF já cadastrado.", exception.getMessage());
    }

    @Test
    void shouldNotThrowsUserAlreadyExistsExceptionWhenCpfNotAlreadyExists() {
        when(user.getCpf()).thenReturn(CPF1);
        when(userDto.cpf()).thenReturn(CPF2);

        assertDoesNotThrow(() -> validation.validate(user, userDto));
    }
}