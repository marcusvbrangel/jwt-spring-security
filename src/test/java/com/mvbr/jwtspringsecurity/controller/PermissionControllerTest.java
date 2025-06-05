package com.mvbr.jwtspringsecurity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class PermissionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHasPermission() throws Exception {
        mockMvc.perform(get("/api/permissions/has-permission")
                .param("username", "admin")
                .param("permission", "READ"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    public void testHasRole() throws Exception {
        mockMvc.perform(get("/api/permissions/has-role")
                .param("username", "admin")
                .param("role", "ADMIN"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    public void testGetUserRoles() throws Exception {
        mockMvc.perform(get("/api/permissions/roles")
                .param("username", "admin"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetUserPermissions() throws Exception {
        mockMvc.perform(get("/api/permissions/permissions")
                .param("username", "admin"))
            .andExpect(status().isOk());
    }
}

