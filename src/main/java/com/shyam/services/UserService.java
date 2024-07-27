package com.shyam.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shyam.dto.CustomerDTO;
import com.shyam.dto.VenderDTO;
import com.shyam.entities.UserEntity;
import com.shyam.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    public UserEntity insertCustomer(CustomerDTO customer) {
        UserEntity newUser = new UserEntity();

        newUser.setRole("CUSTOMER");
        newUser.setEmail(customer.getEmail());
        newUser.setAddress(customer.getAddress());
        newUser.setUsername(customer.getUsername());
        newUser.setPhoneNumber(customer.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(customer.getPassword()));

        UserEntity user = userRepository.save(newUser);
        
        return user;
    }
    
    public UserEntity insertVender(VenderDTO vender) {
        UserEntity newUser = new UserEntity();

        newUser.setRole("VENDER");
        newUser.setEmail(vender.getEmail());
        newUser.setAddress(vender.getAddress());
        newUser.setUsername(vender.getUsername());
        newUser.setWebsiteUrl(vender.getWebsiteUrl());
        newUser.setPhoneNumber(vender.getPhoneNumber());
        newUser.setCompanyName(vender.getCompanyName());
        newUser.setDescription(vender.getDescription());
        newUser.setCompanyAddress(vender.getCompanyAddress());
        newUser.setPassword(passwordEncoder.encode(vender.getPassword()));

        return userRepository.save(newUser);
    }

    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity validateToken(String token) {
        return userRepository.findByUniqueToken(token);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    } 

    public UserEntity getByCompanyName(String name) {
        return userRepository.findByCompanyName(name);
    }
}
