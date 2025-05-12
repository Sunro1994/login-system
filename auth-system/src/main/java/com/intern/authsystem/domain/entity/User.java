package com.intern.authsystem.domain.entity;

import com.intern.authsystem.common.BaseEntity;
import com.intern.authsystem.domain.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Column(name="user_email" , nullable = false)
    private String email;

    @Column(name = "user_password" , nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    private Role role;

    @Column(name = "user_nickname" , nullable = false)
    private String nickname;

    @Builder
    public User(String email,
                String password,
                String nickname) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.nickname = nickname;
    }

    /**
     * 회원가입 시 초기화 메서드 (Factory 메서드 느낌)
     */
    public static User createNewUser(String email, String password, String nickname) {
        return User.builder()
                   .email(email)
                   .password(password)
                   .nickname(nickname)
                   .build();
    }

    /**
     * 관리자 권한 부여 (역할 추가 비즈니스 로직)
     */
    public void promoteToAdmin() {
        this.role = Role.ADMIN;
    }

}
