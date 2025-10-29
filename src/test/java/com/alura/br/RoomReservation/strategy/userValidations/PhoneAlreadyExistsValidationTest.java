package com.alura.br.RoomReservation.strategy.userValidations;

import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneAlreadyExistsValidationTest {

    private static final String PHONE1 = "(51) 99999-9999";
    private static final String PHONE2 = "(51) 88888-8888";

    @InjectMocks
    private PhoneAlreadyExistsValidation validation;

    @Mock
    private User user;

    @Mock
    private UserDto userDto;

    @Test
    void shouldThrowsUserAlreadyExistsWhenEmailAlreadyUsed () {
        when(user.getPhone()).thenReturn(PHONE1);
        when(userDto.phone()).thenReturn(PHONE1);

        var exception = assertThrows(UserAlreadyExistsException.class,
                () -> validation.validate(user, userDto));

        assertEquals("Telefone já cadastrado.", exception.getMessage());
    }

    @Test
    void shouldNotThrowsUserAlreadyExistsWhenEmailNotUsed () {
        when(user.getPhone()).thenReturn(PHONE1);
        when(userDto.phone()).thenReturn(PHONE2);

        assertDoesNotThrow(() -> validation.validate(user, userDto));
    }
}