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
public class ConsentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAcceptConsent() throws Exception {
        mockMvc.perform(post("/api/consent/accept")
                .param("username", "testuser")
                .param("consentType", "TERMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Consentimento aceito")));
    }

    @Test
    public void testRejectConsent() throws Exception {
        mockMvc.perform(post("/api/consent/reject")
                .param("username", "testuser")
                .param("consentType", "TERMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Consentimento rejeitado")));
    }

    @Test
    public void testHasConsented() throws Exception {
        mockMvc.perform(post("/api/consent/accept")
                .param("username", "testuser2")
                .param("consentType", "PRIVACY"))
            .andExpect(status().isOk());
        mockMvc.perform(get("/api/consent/status")
                .param("username", "testuser2")
                .param("consentType", "PRIVACY"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}

