package com.mvbr.jwtspringsecurity.dto;

import com.mvbr.jwtspringsecurity.model.Role;

import java.util.List;

public record CreateUserResponse(
        String email,
        String nome,
        List<Role> roles
) {}