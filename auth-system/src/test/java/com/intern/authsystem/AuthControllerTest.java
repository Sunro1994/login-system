package com.intern.authsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.authsystem.application.AuthService;
import com.intern.authsystem.common.exception.InvalidCredentialsException;
import com.intern.authsystem.common.exception.UserAlreadyExistsException;
import com.intern.authsystem.domain.constant.Role;
import com.intern.authsystem.presentation.AuthController;
import com.intern.authsystem.presentation.dto.request.LoginRequest;
import com.intern.authsystem.presentation.dto.request.SignUpRequest;
import com.intern.authsystem.presentation.dto.response.LoginResponse;
import com.intern.authsystem.presentation.dto.response.SignUpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @WithMockUser
    @Test
    void 회원가입_성공() throws Exception {
        SignUpRequest request = new SignUpRequest("email@email.com", "password", "nickname");
        SignUpResponse response = SignUpResponse.of("email@email.com", "nickname", Role.USER);

        given(authService.signup(any(SignUpRequest.class))).willReturn(response);

        mockMvc.perform(post("/sign-up")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.email").value("email@email.com"))
               .andExpect(jsonPath("$.nickname").value("nickname"))
               .andExpect(jsonPath("$.role").value("USER"));
    }

    @WithMockUser
    @Test
    void 회원가입_실패_이미가입된사용자() throws Exception {
        SignUpRequest request = new SignUpRequest("email@email.com", "password", "nickname");

        given(authService.signup(any(SignUpRequest.class)))
                .willThrow(new UserAlreadyExistsException("이미 가입된 사용자입니다."));

        mockMvc.perform(post("/sign-up")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
               .andExpect(status().isConflict())
               .andExpect(jsonPath("$.error.code").value("USER_ALREADY_EXISTS"))
               .andExpect(jsonPath("$.error.message").value("이미 가입된 사용자입니다."));
    }

    @WithMockUser
    @Test
    void 로그인_성공() throws Exception {
        LoginRequest request = new LoginRequest("user@email.com", "password");
        LoginResponse response = new LoginResponse("mocked-jwt-token");

        given(authService.login(any(LoginRequest.class))).willReturn(response);

        String content = mockMvc.perform(post("/login")
                                                 .with(csrf())
                                                 .contentType(MediaType.APPLICATION_JSON)
                                                 .content(new ObjectMapper().writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.token").value("mocked-jwt-token"))
                                .andReturn().getResponse().getContentAsString();

        assertThat(content).isNotEmpty();
    }

    @WithMockUser
    @Test
    void 로그인_실패_잘못된_비밀번호() throws Exception {
        LoginRequest request = new LoginRequest("user@email.com", "wrong-password");

        given(authService.login(any(LoginRequest.class)))
                .willThrow(new InvalidCredentialsException());

        mockMvc.perform(post("/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
               .andExpect(status().isUnauthorized())
               .andExpect(jsonPath("$.error.code").value("INVALID_CREDENTIALS"))
               .andExpect(jsonPath("$.error.message").value("아이디 또는 비밀번호가 올바르지 않습니다."));
    }


}