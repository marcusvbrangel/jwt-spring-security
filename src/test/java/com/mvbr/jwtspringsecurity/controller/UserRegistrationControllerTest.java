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
public class UserRegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterUser() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser10")
                .param("email", "testuser10@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Usuário registrado")));
    }

    @Test
    public void testRegisterUserWithExistingUsername() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser11")
                .param("email", "testuser11@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser11")
                .param("email", "testuser12@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testRegisterUserWithExistingEmail() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser13")
                .param("email", "testuser13@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser14")
                .param("email", "testuser13@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testRegisterUserWithWeakPassword() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser15")
                .param("email", "testuser15@email.com")
                .param("password", "123"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testRegisterAndConfirmEmail() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .param("username", "testuser16")
                .param("email", "testuser16@email.com")
                .param("password", "SenhaForte123"))
                .andExpect(status().isOk());
        // Simula confirmação de e-mail (token fictício)
        mockMvc.perform(post("/api/auth/confirm-email")
                .param("token", "algum-token"))
                .andExpect(status().isOk());
    }
}
