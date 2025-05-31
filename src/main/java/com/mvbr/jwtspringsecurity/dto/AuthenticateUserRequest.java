package com.mvbr.jwtspringsecurity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_EMAIL_INVALIDO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_EMAIL_OBRIGATORIO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_SENHA_OBRIGATORIA;

public record AuthenticateUserRequest(
        @NotBlank(message = MSG_EMAIL_OBRIGATORIO)
        @Email(message = MSG_EMAIL_INVALIDO)
        String email,
        @NotBlank(message = MSG_SENHA_OBRIGATORIA)
        String password
) {}

