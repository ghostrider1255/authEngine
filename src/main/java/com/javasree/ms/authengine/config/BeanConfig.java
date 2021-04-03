package com.javasree.ms.authengine.config;

import com.javasree.ms.authengine.model.dto.PrivilegeDto;
import com.javasree.ms.authengine.model.dto.RoleDto;
import com.javasree.ms.authengine.model.dto.UserDto;
import com.javasree.ms.authengine.model.entity.Privilege;
import com.javasree.ms.authengine.model.entity.Role;
import com.javasree.ms.authengine.model.entity.User;
import com.javasree.ms.authengine.model.vo.UserResponseVO;
import com.javasree.ms.authengine.model.vo.UserVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncode(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(PrivilegeDto.class, Privilege.class)
            .addMapping( PrivilegeDto::getName, Privilege::setName);

        modelMapper.createTypeMap(RoleDto.class, Role.class)
            .addMapping( RoleDto::getName, Role::setName)
            .addMapping( RoleDto::getPrivileges, Role::setPrivileges);

        modelMapper.createTypeMap(Role.class, RoleDto.class)
            .addMapping( Role::getName, RoleDto::setName)
            .addMapping( Role::getPrivileges, RoleDto::setPrivileges);

        modelMapper.createTypeMap(UserVO.class, UserDto.class)
            .addMapping( UserVO::getFirstName, UserDto::setFirstName)
            .addMapping( UserVO::getLastName, UserDto::setLastName)
            .addMapping( UserVO::getEmail, UserDto::setEmail)
            .addMapping( UserVO::getPassword, UserDto::setEncryptedPassword)
            .addMapping( UserVO::getRoles , UserDto::setRoles);

        modelMapper.createTypeMap(UserDto.class, UserResponseVO.class)
                .addMapping( UserDto::getFirstName , UserResponseVO::setFirstName)
                .addMapping( UserDto::getLastName, UserResponseVO::setLastName)
                .addMapping( UserDto::getUserId, UserResponseVO::setUserId)
                .addMapping( UserDto::getEmail, UserResponseVO::setEmail);

        modelMapper.createTypeMap(UserDto.class, User.class)
                .addMapping( UserDto::getFirstName, User::setFirstName)
                .addMapping( UserDto::getLastName, User::setLastName)
                .addMapping( UserDto::getEmail, User::setEmail)
                .addMapping( UserDto::getEncryptedPassword, User::setEncryptedPassword)
                .addMapping( UserDto::getUserId , User::setUserId);

        modelMapper.createTypeMap(User.class,UserDto.class)
                .addMapping( User::getFirstName, UserDto::setFirstName)
                .addMapping( User::getFirstName, UserDto::setLastName)
                .addMapping( User::getEmail , UserDto::setEmail)
                .addMapping( User::getUserId, UserDto::setUserId)
                .addMapping( User::getEncryptedPassword, UserDto::setEncryptedPassword);

        return modelMapper;
    }
}
