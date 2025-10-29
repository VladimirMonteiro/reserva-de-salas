package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.user.CreateUserRequestDto;
import com.alura.br.RoomReservation.dto.user.UpdateUserDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
import com.alura.br.RoomReservation.strategy.userValidations.CpfAlreadyExistsValidation;
import com.alura.br.RoomReservation.strategy.userValidations.EmailAlreadyExistsValidation;
import com.alura.br.RoomReservation.strategy.userValidations.PhoneAlreadyExistsValidation;
import com.alura.br.RoomReservation.strategy.userValidations.UserValidationsStategy;
import com.alura.br.RoomReservation.utils.UserMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private List<UserValidationsStategy> validations;

    @Mock
    private CpfAlreadyExistsValidation cpfValidation;

    @Mock
    private PhoneAlreadyExistsValidation phoneValidation;

    @Mock
    private EmailAlreadyExistsValidation emailValidation;

    private User user;
    private CreateUserRequestDto createUserRequestDto;
    private UpdateUserDto updateUserDto;

    @BeforeEach
    void setUp() {
        user = buildUser();
        createUserRequestDto = buildCreateUserRequestDto();
        updateUserDto = buildUpdateUserRequestDto();
    }

    @Test
    void shouldCreateUserWhenInputsAreValids() {
        when(userRepository.findByCpfOrPhoneOrEmail(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        var result = userService.createUser(createUserRequestDto);

        assertNotNull(result);
        assertEquals(createUserRequestDto.name(), user.getName());
        assertEquals(createUserRequestDto.cpf(), user.getCpf());
        assertEquals(createUserRequestDto.age(), user.getAge());
        assertEquals(createUserRequestDto.phone(), user.getPhone());
        assertEquals(createUserRequestDto.email(), user.getEmail());

        verify(userRepository, times(1)).findByCpfOrPhoneOrEmail(anyString(), anyString(), anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldCallsValidationsWithUserAlreadyExists() {

        when(userRepository.findByCpfOrPhoneOrEmail(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        validations = new ArrayList<>(List.of(cpfValidation, phoneValidation, emailValidation));
        ReflectionTestUtils.setField(userService, "validations", validations);

        userService.createUser(createUserRequestDto);

        verify(cpfValidation).validate(any(), any());
        verify(phoneValidation).validate(any(), any());
        verify(emailValidation).validate(any(), any());
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

    @Test
    void shouldUpdateUserWhenInputsAreValid() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByCpfOrPhoneOrEmail(updateUserDto.cpf(), updateUserDto.phone(), updateUserDto.email()))
                .thenReturn(Optional.empty());

        var updatedUser = UserMapper.updateEntityFromDto(updateUserDto, user);
        updatedUser.setId(userId);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDto result = userService.updateUser(updateUserDto, userId);
        assertNotNull(result);
        assertEquals(updateUserDto.name(), result.name());
        assertEquals(updateUserDto.cpf(), result.cpf());
        assertEquals(updateUserDto.phone(), result.phone());
        assertEquals(updateUserDto.email(), result.email());
        assertEquals(updateUserDto.age(), result.age());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1))
                .findByCpfOrPhoneOrEmail(updateUserDto.cpf(), updateUserDto.phone(), updateUserDto.email());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void shouldCallValidationsWhenDuplicateExists() {

        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setCpf(updateUserDto.cpf());
        otherUser.setPhone(updateUserDto.phone());
        otherUser.setEmail(updateUserDto.email());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findByCpfOrPhoneOrEmail(updateUserDto.cpf(), updateUserDto.phone(), updateUserDto.email()))
                .thenReturn(Optional.of(otherUser));
        when(userRepository.save(any(User.class))).thenReturn(user);

        validations = new ArrayList<>(List.of(cpfValidation, phoneValidation, emailValidation));
        ReflectionTestUtils.setField(userService, "validations", validations);

        userService.updateUser(updateUserDto, user.getId());

        verify(cpfValidation).validate(eq(user), any());
        verify(phoneValidation).validate(eq(user), any());
        verify(emailValidation).validate(eq(user), any());
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

    private UpdateUserDto buildUpdateUserRequestDto() {
        return new UpdateUserDto("João Atualizado", "123.456.789-00", 30, "(11) 98888-7777",
                "joao@gmail.com");
    }
}