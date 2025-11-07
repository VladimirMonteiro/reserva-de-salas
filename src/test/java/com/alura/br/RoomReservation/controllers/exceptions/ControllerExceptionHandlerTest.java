package com.alura.br.RoomReservation.controllers.exceptions;

import com.alura.br.RoomReservation.controllers.UserController;
import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import com.alura.br.RoomReservation.services.implementations.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class ControllerExceptionHandlerTest {

    private static final String PATH = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = buildUserDto();
    }

    @Test
    void methodArgumentNotValidException() {
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        when(userService.findById(99L)).thenThrow(new ObjectNotFoundException("Usuário não encontrado"));

        mockMvc.perform(get(PATH + "/" + 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.fieldErrors.notFound").value("Usuário não encontrado"));
    }

    @Test
    void shouldReturn406WhenInputAreInvalid() throws Exception {
        when(userService.createUser(any(CreateUserRequestDto.class)))
                .thenThrow(new UserAlreadyExistsException("CPF já cadastrado."));

        var json = mapper.writeValueAsString(userDto);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Usuário inválido"))
                .andExpect(jsonPath("$.fieldErrors.user").value("CPF já cadastrado."));

        verify(userService, times(1)).createUser(any(CreateUserRequestDto.class));

    }

    @Test
    void shouldReturn400WhenDataAreInvalid() throws Exception {

        var invalidUser = "{}";

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUser))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.fieldErrors.name").value("O nome é obrigatório."))
                .andExpect(jsonPath("$.fieldErrors.cpf").value("O CPF é obrigatório."))
                .andExpect(jsonPath("$.fieldErrors.age").value("A idade é obrigatório."))
                .andExpect(jsonPath("$.fieldErrors.email").value("O e-mail é obrigatório,"));

        verify(userService, times(0)).createUser(any());
    }

    private UserDto buildUserDto() {
        return new UserDto(1L, "Ana", "791.534.408-03", 25, "(11) 99999-9999", "ana@email.com");
    }
}