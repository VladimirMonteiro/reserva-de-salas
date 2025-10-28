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
class EmailAlreadyExistsValidationTest {

    private static final String EMAIL1 = "test@email.com";
    private static final String EMAIL2 = "test2@email.com";

    @InjectMocks
    EmailAlreadyExistsValidation validation;

    @Mock
    private User user;

    @Mock
    private UserDto userDto;

    @Test
    void shouldThrowsUserAlreadyExistsWhenEmailAlreadyUsed () {
        when(user.getEmail()).thenReturn(EMAIL1);
        when(userDto.email()).thenReturn(EMAIL1);

        var exception = assertThrows(UserAlreadyExistsException.class,
                () -> validation.validate(user, userDto));

        assertEquals("E-mail já cadastrado.", exception.getMessage());
    }

    @Test
    void shouldNotThrowsUserAlreadyExistsWhenEmailNotUsed () {
        when(user.getEmail()).thenReturn(EMAIL1);
        when(userDto.email()).thenReturn(EMAIL2);

        assertDoesNotThrow(() -> validation.validate(user, userDto));
    }
}