package com.rs01.security;

import com.rs01.user.entity.UserRole;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public Long getCurrentUserId() { return currentPrincipal().getUserId(); }
    public String getCurrentUsername() { return currentPrincipal().getUsername(); }
    public UserRole getCurrentRole() { return currentPrincipal().getRole(); }

    private CustomUserPrincipal currentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user");
        }
        return principal;
    }
}
