package com.mvbr.jwtspringsecurity.dto;

import com.mvbr.jwtspringsecurity.model.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role
) {}