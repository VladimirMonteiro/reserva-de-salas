package com.alura.br.RoomReservation.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record CreateUserRequestDto(@NotBlank(message = "O nome é obrigatório.") String name,
                                   @NotBlank(message = "O CPF é obrigatório.") @CPF String cpf,
                                   @NotNull(message = "A idade é obrigatório.") Integer age,
                                   @NotBlank(message = "O e-mail é obrigatório,") @Email String email,

                                   @NotBlank(message = "O telefone é obrigatório.")
                      @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}",
                              message = "Telefone deve seguir o padrão (XX) XXXXX-XXXX") String phone) {
}
