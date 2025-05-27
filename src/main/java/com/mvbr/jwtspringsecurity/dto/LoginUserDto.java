package com.mvbr.jwtspringsecurity.dto;

public record LoginUserDto(
        String email,
        String password
) {}