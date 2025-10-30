package com.alura.br.RoomReservation.controllers.exceptions;

import com.alura.br.RoomReservation.controllers.UserController;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
import com.alura.br.RoomReservation.services.implementations.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
class ControllerExceptionHandlerTest {

    private static final String PATH = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;


    @Test
    void methodArgumentNotValidException () {
    }

    @Test
    void shouldReturn404WhenUserNotFound () throws Exception {
        when(userService.findById(99L)).thenThrow(new ObjectNotFoundException("Usuário não encontrado"));

        mockMvc.perform(get(PATH + "/" + 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.fieldErrors.notFound").value("Usuário não encontrado"));
    }
}