package com.alura.br.RoomReservation.controllers;

import org.springframework.web.bind.annotation.*;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.services.implementations.UserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
