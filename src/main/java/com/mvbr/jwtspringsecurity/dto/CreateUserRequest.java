package com.mvbr.jwtspringsecurity.dto;

import com.mvbr.jwtspringsecurity.model.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_EMAIL_INVALIDO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_EMAIL_OBRIGATORIO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_NOME_OBRIGATORIO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_PAPEL_USUARIO_OBRIGATORIO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_SENHA_OBRIGATORIA;

public record CreateUserRequest(
        @NotBlank(message = MSG_EMAIL_OBRIGATORIO)
        @Email(message = MSG_EMAIL_INVALIDO)
        String email,
        @NotBlank(message = MSG_SENHA_OBRIGATORIA)
        String password,
        @NotBlank(message = MSG_NOME_OBRIGATORIO)
        String nome,
        @NotNull(message = MSG_PAPEL_USUARIO_OBRIGATORIO)
        RoleName role
) {}
