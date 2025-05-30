package com.mvbr.jwtspringsecurity.dto;

public record CreateUserResponse(
        Long id,
        String email,
        String nome
) {}