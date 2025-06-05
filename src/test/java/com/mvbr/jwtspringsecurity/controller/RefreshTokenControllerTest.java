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
public class RefreshTokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRefreshToken() throws Exception {
        // Simula refresh de sessão (token fictício)
        mockMvc.perform(post("/api/auth/refresh-token")
                .param("refreshToken", "token-fake"))
                .andExpect(status().isOk());
    }
}

