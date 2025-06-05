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
public class TwoFactorAuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEnable2FA() throws Exception {
        mockMvc.perform(post("/api/2fa/enable")
                .param("username", "user"))
                .andExpect(status().isOk())
                .andExpect(content().string("2FA habilitado."));
    }

    @Test
    public void testDisable2FA() throws Exception {
        mockMvc.perform(post("/api/2fa/disable")
                .param("username", "user"))
                .andExpect(status().isOk())
                .andExpect(content().string("2FA desabilitado."));
    }

    @Test
    public void testSend2FACode() throws Exception {
        mockMvc.perform(post("/api/2fa/send")
                .param("username", "user")
                .param("destination", "user@email.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Código 2FA enviado."));
    }

    @Test
    public void testIs2FAEnabled() throws Exception {
        mockMvc.perform(post("/api/2fa/enable")
                .param("username", "user2"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/2fa/status")
                .param("username", "user2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

