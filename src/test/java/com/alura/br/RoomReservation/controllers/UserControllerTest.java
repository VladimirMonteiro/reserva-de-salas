package com.alura.br.RoomReservation.controllers;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UpdateUserDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.services.implementations.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String PATH = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void setUp () {
        this.userDto = buildUserDto();
    }

    @Test
    void shouldReturn201AndUserDto () throws Exception {

        when(userService.createUser(any(CreateUserRequestDto.class)))
                .thenReturn(userDto);

        var createUserDto = new CreateUserRequestDto("Ana",
                "791.534.408-03",
                25,
                "ana@email.com",
                "(11) 99999-9999"
        );

        var json = mapper.writeValueAsString(createUserDto);
        System.out.println(json);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(createUserDto.name()))
                .andExpect(jsonPath("$.cpf").value(createUserDto.cpf()))
                .andExpect(jsonPath("$.age").value(createUserDto.age()))
                .andExpect(jsonPath("$.email").value(createUserDto.email()))
                .andExpect(jsonPath("$.phone").value(createUserDto.phone()));

        verify(userService).createUser(any(CreateUserRequestDto.class));
    }

    @Test
    void shouldReturn200AndUserIfExists () throws Exception {
        Long id = 1L;
        when(userService.findById(id)).thenReturn(userDto);

        mockMvc.perform(get(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.name()))
                .andExpect(jsonPath("$.cpf").value(userDto.cpf()))
                .andExpect(jsonPath("$.age").value(userDto.age()))
                .andExpect(jsonPath("$.email").value(userDto.email()))
                .andExpect(jsonPath("$.phone").value(userDto.phone()));

        verify(userService, times(1)).findById(1L);
    }

    @Test
    void shouldReturn200AndOneListOfUsers () throws Exception {
        when(userService.findAll(0, 20)).thenReturn(List.of(userDto));

        mockMvc.perform(get(PATH)
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(userDto.name()))
                .andExpect(jsonPath("$[0].cpf").value(userDto.cpf()))
                .andExpect(jsonPath("$[0].age").value(userDto.age()))
                .andExpect(jsonPath("$[0].email").value(userDto.email()))
                .andExpect(jsonPath("$[0].phone").value(userDto.phone()));

        verify(userService).findAll(0, 20);
    }

    @Test
    void shouldReturn204AndDeleteUserIfExists () throws Exception {
        Long id = 1L;
        doNothing().when(userService).deleteUser(id);

        mockMvc.perform(delete(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    void shouldReturn200AndUserUpdated () throws Exception {
        Long id = 1L;
        var updateUserDto = new UpdateUserDto(
                "Ana Lima",
                "791.534.408-03",
                30,
                "ana2@email.com",
                "(11) 99999-8888");

        var userUpdated = new UserDto("Ana Lima",
                "791.534.408-03",
                30,
                "(11) 99999-8888",
                "ana2@email.com");

        var json = mapper.writeValueAsString(updateUserDto);

        when(userService.updateUser(updateUserDto, id)).thenReturn(userUpdated);

        mockMvc.perform(put(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userUpdated.name()))
                .andExpect(jsonPath("$.age").value(userUpdated.age()))
                .andExpect(jsonPath("$.phone").value(userUpdated.phone()))
                .andExpect(jsonPath("$.email").value(updateUserDto.email()));

        verify(userService, times(1)).updateUser(updateUserDto, id);
    }

    private UserDto buildUserDto () {
        return new UserDto("Ana", "791.534.408-03", 25, "(11) 99999-9999", "ana@email.com");
    }
}