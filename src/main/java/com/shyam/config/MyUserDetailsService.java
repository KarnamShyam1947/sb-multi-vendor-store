package com.shyam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shyam.entities.UserEntity;
import com.shyam.repositories.UserRepository;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);

        if(user == null) 
            throw new UsernameNotFoundException("Invalid user details");
        
        else
            return new MyUserDetails(user);
    }
    
}
