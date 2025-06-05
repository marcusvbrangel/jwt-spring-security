package com.mvbr.jwtspringsecurity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordRecoveryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRecoverPassword() throws Exception {
        mockMvc.perform(post("/api/password/recover")
                .param("email", "testuser@email.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Token de recuperação enviado")));
    }

    @Test
    public void testResetPassword() throws Exception {
        mockMvc.perform(post("/api/password/reset")
                .param("token", "algum-token")
                .param("newPassword", "SenhaForte123"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Senha redefinida")));
    }
}

