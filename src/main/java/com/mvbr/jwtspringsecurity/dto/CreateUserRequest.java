package com.mvbr.jwtspringsecurity.dto;

import com.mvbr.jwtspringsecurity.model.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password,
        @NotBlank(message = "O nome é obrigatório.")
        String nome,
        @NotNull(message = "O papel do usuário é obrigatório.")
        RoleName role
) {}

