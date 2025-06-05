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
public class LoginAttemptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRecordLoginAttempt() throws Exception {
        mockMvc.perform(post("/api/login-attempts/record")
                .param("username", "testuser")
                .param("success", "false"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Tentativa registrada")));
    }

    @Test
    public void testIsAccountLocked() throws Exception {
        // Simula várias tentativas para bloquear
        for (int i = 0; i < 6; i++) {
            mockMvc.perform(post("/api/login-attempts/record")
                    .param("username", "testuser2")
                    .param("success", "false"))
                .andExpect(status().isOk());
        }
        mockMvc.perform(get("/api/login-attempts/locked")
                .param("username", "testuser2"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    public void testUnlockAccount() throws Exception {
        mockMvc.perform(post("/api/login-attempts/unlock")
                .param("username", "testuser2"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Conta desbloqueada")));
    }
}

