package com.javasree.ms.authengine.service;

import com.javasree.ms.authengine.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
    UserDto updateUser(UserDto userDetails);
}
