package com.mvbr.jwtspringsecurity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser1")
                .param("email", "testuser1@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Usuário registrado")));
    }

    @Test
    public void testLogin() throws Exception {
        // Primeiro registra o usuário
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser2")
                .param("email", "testuser2@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk());
        // Depois faz login
        mockMvc.perform(post("/api/auth/login")
                .param("username", "testuser2")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterWithWeakPassword() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser3")
                .param("email", "testuser3@email.com")
                .param("password", "123"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testConfirmEmail() throws Exception {
        // Simula registro e confirmação de e-mail
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser4")
                .param("email", "testuser4@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk());
        // O token normalmente seria enviado por e-mail, aqui simulamos um token qualquer
        mockMvc.perform(post("/api/auth/confirm-email")
                .param("token", "algum-token"))
                .andExpect(status().isOk());
    }
}
