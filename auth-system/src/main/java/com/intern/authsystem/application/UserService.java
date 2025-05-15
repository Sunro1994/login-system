package com.intern.authsystem.application;

import com.intern.authsystem.common.exception.UserNotFoundException;
import com.intern.authsystem.domain.entity.User;
import com.intern.authsystem.domain.repository.UserRepository;
import com.intern.authsystem.presentation.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 관리자 권한 부여
     */
    @Transactional
    public SignUpResponse promoteToAdmin(Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new UserNotFoundException(userId));

        user.promoteToAdmin();

        return SignUpResponse.of(user.getEmail(),
                                 user.getNickname(),
                                 user.getRole());
    }
}