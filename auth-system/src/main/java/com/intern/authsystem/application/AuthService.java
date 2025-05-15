package com.intern.authsystem.application;

import com.intern.authsystem.common.exception.InvalidCredentialsException;
import com.intern.authsystem.common.exception.UserAlreadyExistsException;
import com.intern.authsystem.domain.entity.User;
import com.intern.authsystem.domain.repository.UserRepository;
import com.intern.authsystem.infrastructure.security.JwtTokenProvider;
import com.intern.authsystem.presentation.dto.request.LoginRequest;
import com.intern.authsystem.presentation.dto.request.SignUpRequest;
import com.intern.authsystem.presentation.dto.response.LoginResponse;
import com.intern.authsystem.presentation.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public SignUpResponse signup(SignUpRequest request) {
        if (userRepository.existsByUsername(request.email())) {
            throw new UserAlreadyExistsException(request.email());
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.createNewUser(request.email(), encodedPassword, request.nickname());

        User savedUser = userRepository.save(user);

        return SignUpResponse.of(savedUser.getEmail(),savedUser.getNickname(),savedUser.getRole());
    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.email())
                                  .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());

        return new LoginResponse(token);
    }
}