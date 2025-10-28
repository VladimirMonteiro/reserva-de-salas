package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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

    private User buildUser() {
        return new User(1L, "Alfredo", "111.111.111-99", 30, "(11) 99999-9999", "test@email.com", null);
    }

    private CreateUserRequestDto buildCreateUserRequestDto() {
        return new CreateUserRequestDto(
                "Alfredo",
                "111.111.111-99",
                30,
                "(11) 99999-9999",
                "test@email.com");
    }
}