package com.intern.authsystem;

import com.intern.authsystem.domain.constant.Role;
import com.intern.authsystem.domain.entity.User;
import com.intern.authsystem.domain.repository.UserRepository;
import com.intern.authsystem.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 관리자_권한_부여_성공() throws Exception {
        // given : 일반 사용자 생성 및 저장
        User user = User.createNewUser("user@email.com", passwordEncoder.encode("password"), "nickname");
        userRepository.save(user);

        // given : 관리자 권한 토큰 생성
        String adminToken = jwtTokenProvider.createToken("admin@email.com", Role.ADMIN);

        // when + then : 관리자 승격 요청 성공
        mockMvc.perform(patch("/admin/users/{userId}/roles", user.getId())
                                .header("Authorization", "Bearer " + adminToken))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void 관리자_권한_부여_실패_권한없음() throws Exception {
        // given : 일반 사용자 생성 및 저장
        User user = User.createNewUser("user@email.com", passwordEncoder.encode("password"), "nickname");
        userRepository.save(user);

        // given : 일반 사용자 권한 토큰 생성
        String userToken = jwtTokenProvider.createToken("user@email.com", Role.USER);

        // when + then : 관리자 승격 요청 실패 (403 Forbidden)
        mockMvc.perform(patch("/admin/users/{userId}/roles", user.getId())
                                .header("Authorization", "Bearer " + userToken))
               .andExpect(status().isForbidden());
    }

    @Test
    void 관리자_권한_부여_실패_유저없음() throws Exception {
        // given : 관리자 권한 토큰 생성
        String adminToken = jwtTokenProvider.createToken("admin@email.com", Role.ADMIN);

        // when + then : 존재하지 않는 유저ID로 요청 시 404 예외 발생
        mockMvc.perform(patch("/admin/users/{userId}/roles", 999L)
                                .header("Authorization", "Bearer " + adminToken))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"));
    }
}