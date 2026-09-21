package com.rs01.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs01.security.CustomUserPrincipal;
import com.rs01.security.JwtUtil;
import com.rs01.user.entity.User;
import com.rs01.user.entity.UserRole;
import com.rs01.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthIntegrationTests {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @Test
    void registerHashesPasswordAndLoginReturnsUsableJwt() throws Exception {
        String username = "auth_test_user";
        String password = "TestPassword123!";

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"auth_test_user","password":"TestPassword123!",
                                 "name":"Auth Test","email":"auth@example.com","phone":"10086"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isNumber());

        User stored = userMapper.selectByUsername(username);
        assertThat(stored.getPassword()).startsWith("$2");
        assertThat(stored.getPassword()).isNotEqualTo(password);
        assertThat(passwordEncoder.matches(password, stored.getPassword())).isTrue();

        String loginBody = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"auth_test_user\",\"password\":\"TestPassword123!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.userId").value(stored.getId()))
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.role").value("CUSTOMER"))
                .andReturn().getResponse().getContentAsString();

        JsonNode data = objectMapper.readTree(loginBody).get("data");
        CustomUserPrincipal principal = jwtUtil.parsePrincipal(data.get("token").asText());
        assertThat(principal.getUserId()).isEqualTo(stored.getId());
        assertThat(principal.getRole()).isEqualTo(UserRole.CUSTOMER);
    }

    @Test
    void permitAllRouteIgnoresInvalidBearerToken() throws Exception {
        mockMvc.perform(get("/health").header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void invalidBearerTokenReturnsUnauthorizedForProtectedRoute() throws Exception {
        mockMvc.perform(get("/reservations").header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));
    }

    @Test
    void protectedRouteRejectsAnonymousRequests() throws Exception {
        mockMvc.perform(get("/reservations/not-mapped"))
                .andExpect(status().isUnauthorized());
    }
}
