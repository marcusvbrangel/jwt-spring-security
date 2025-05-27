package com.mvbr.jwtspringsecurity.dto;

import com.mvbr.jwtspringsecurity.model.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles
) {}