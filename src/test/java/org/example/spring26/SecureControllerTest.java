package org.example.spring26;

import org.example.spring26.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecureController.class)
@Import(SecurityConfig.class)
class SecureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void userCanAccessUserEndpoint() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userCanNotAccessAdminEndpoint() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    void anonymousCannotAccessUserEndpoint() throws Exception {
        // Since you have formLogin enabled, unauthorized users
        // are usually redirected (302) to /login.
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection());
    }
}
