package com.rs01.auth;

import com.rs01.auth.dto.LoginDTO;
import com.rs01.auth.dto.RegisterDTO;
import com.rs01.auth.vo.LoginVO;
import com.rs01.common.exception.BusinessException;
import com.rs01.security.CustomUserPrincipal;
import com.rs01.security.JwtUtil;
import com.rs01.user.entity.User;
import com.rs01.user.entity.UserRole;
import com.rs01.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public Long register(RegisterDTO request) {
        if (userMapper.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(UserRole.CUSTOMER);
        user.setStatus(1);
        userMapper.insert(user);
        log.info("Customer registered: userId={}", user.getId());
        return user.getId();
    }

    public LoginVO login(LoginDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        log.info("User login succeeded: userId={}", principal.getUserId());
        return new LoginVO(jwtUtil.generateToken(principal), principal.getUserId(),
                principal.getUsername(), principal.getRole());
    }
}
