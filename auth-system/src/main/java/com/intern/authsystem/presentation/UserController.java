package com.intern.authsystem.presentation;

import com.intern.authsystem.application.UserService;
import com.intern.authsystem.presentation.dto.response.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 관리자 권한 부여 API
     */
    @Operation(summary = "권한 변경", description = "관리자로 승급합니다.")
    @ApiResponse(responseCode = "200", description = "관리자 승급 성공")
    @PatchMapping("/users/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SignUpResponse> promoteToAdmin(@PathVariable Long userId) {
        SignUpResponse response = userService.promoteToAdmin(userId);
        return ResponseEntity.ok(response);
    }
}