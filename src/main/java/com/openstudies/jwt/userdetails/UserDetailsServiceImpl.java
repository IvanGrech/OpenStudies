package com.openstudies.jwt.userdetails;


import com.openstudies.hibernate.services.UserService;
import com.openstudies.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = service.findByLogin(login);
        if (user != null) {
            return new UserDetailsImpl(user);
        }
        throw new IllegalArgumentException("User not found");
    }
}