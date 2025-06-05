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
public class RemoteLogoutControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testForceLogout() throws Exception {
        mockMvc.perform(post("/api/logout-remote/force")
                .param("username", "testuser"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Logout remoto forçado")));
    }

    @Test
    public void testIsUserLoggedOut() throws Exception {
        mockMvc.perform(post("/api/logout-remote/force")
                .param("username", "testuser2"))
            .andExpect(status().isOk());
        mockMvc.perform(get("/api/logout-remote/status")
                .param("username", "testuser2"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}

