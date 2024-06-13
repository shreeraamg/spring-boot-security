package com.shreeraam.userservice.service;

import com.shreeraam.userservice.model.User;
import com.shreeraam.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Username is Email in this project
        Optional<User> userWithGivenEmail = userRepository.findByEmail(username);

        return userWithGivenEmail.get();
    }
}
