package com.colorpalettegenerator.service;

import com.colorpalettegenerator.model.User;
import com.colorpalettegenerator.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    User save(UserRegistrationDto registrationDto);
}
