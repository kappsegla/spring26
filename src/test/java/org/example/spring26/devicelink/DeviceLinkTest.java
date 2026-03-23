package org.example.spring26.devicelink;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class DeviceLinkTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceLinkTokenRepository repo;

    @Test
    @WithMockUser(username = "testuser")
    public void testCreateAndUseLink() throws Exception {
        // 1. Create link
        String responseBody = mockMvc.perform(post("/device-link/create").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists())
                .andReturn().getResponse().getContentAsString();

        String url = responseBody.substring(responseBody.indexOf("http"));
        url = url.substring(0, url.length() - 2); // remove "}
        String token = url.substring(url.indexOf("token=") + 6);

        // 2. Use link
        mockMvc.perform(get("/device-link").param("token", token))
                .andExpect(status().isFound()) // Redirects to /add-passkey
                .andExpect(redirectedUrl("/add-passkey"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testCreateAndUseLinkAndCheckAuth() throws Exception {
        // 1. Create link
        String responseBody = mockMvc.perform(post("/device-link/create").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists())
                .andReturn().getResponse().getContentAsString();

        String url = responseBody.substring(responseBody.indexOf("http"));
        url = url.substring(0, url.length() - 2); // remove "}
        String token = url.substring(url.indexOf("token=") + 6);

        // 2. Use link
        var mvcResult = mockMvc.perform(get("/device-link").param("token", token))
                .andExpect(status().isFound()) // Redirects to /add-passkey
                .andExpect(redirectedUrl("/add-passkey"))
                .andReturn();

        var session = mvcResult.getRequest().getSession();

        // 3. Follow redirect
        mockMvc.perform(get("/add-passkey").session((org.springframework.mock.web.MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    assert auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEMP"));
                });
    }
}
