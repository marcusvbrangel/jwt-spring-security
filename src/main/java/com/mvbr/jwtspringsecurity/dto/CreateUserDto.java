package com.mvbr.jwtspringsecurity.dto;

import com.mvbr.jwtspringsecurity.model.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password,
        @NotNull(message = "O papel do usuário é obrigatório.")
        RoleName role
) {}

