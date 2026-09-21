package com.rs01.security;

import com.rs01.user.entity.User;
import com.rs01.user.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserPrincipal implements UserDetails {
    private final Long userId;
    private final String username;
    private final String password;
    private final UserRole role;
    private final boolean enabled;

    public CustomUserPrincipal(Long userId, String username, String password, UserRole role, boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public static CustomUserPrincipal from(User user) {
        return new CustomUserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole(),
                Integer.valueOf(1).equals(user.getStatus()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return enabled; }
}
