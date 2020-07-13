package com.openstudies.jwt.userdetails;




import java.util.Collection;
import java.util.Collections;

import com.openstudies.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRole = user.getRole().getName().toUpperCase();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return "{noop}"+user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
