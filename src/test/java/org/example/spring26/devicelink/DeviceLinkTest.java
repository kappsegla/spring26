package org.example.spring26.devicelink;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceLinkTest {

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
    public void testUseInvalidToken() throws Exception {
        mockMvc.perform(get("/device-link").param("token", "invalid-token"))
                .andExpect(status().isUnauthorized());
    }
}
