package com.javasree.ms.authengine.service.impl;

import com.javasree.ms.authengine.model.dto.UserDto;
import com.javasree.ms.authengine.model.entity.Privilege;
import com.javasree.ms.authengine.model.entity.Role;
import com.javasree.ms.authengine.model.entity.User;
import com.javasree.ms.authengine.repository.RoleRepository;
import com.javasree.ms.authengine.repository.UserRepository;
import com.javasree.ms.authengine.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private RoleRepository roleRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getEncryptedPassword()));
        User user = this.modelMapper.map(userDetails, User.class);
        userRepository.save(user);
        //var userDto =
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User userEntity = userRepository.findByEmail(username);
        if (null == userEntity){
            throw new UsernameNotFoundException(username);
            //new userdetails.User(" "," ",true,true,true,true,
            //getAuthorities(util.Arrays.asList(roleRepository.findByName("ROLE_USER"))))
        }

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
                true,true,true,true,
                getAuthorities(userEntity.getRoles()));
    }

    private Collection<GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedPrivileges(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantedPrivileges(List<String> privileges){
        List<GrantedAuthority> authorities  = new ArrayList<GrantedAuthority>();
        privileges.forEach( privilege ->{
                authorities.add(new SimpleGrantedAuthority(privilege));
        });
        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges  = new ArrayList<String>();
        List<Privilege> collection = new ArrayList<Privilege>();
        roles.forEach( role ->
                collection.addAll(role.getPrivileges())
            );
        collection.forEach( privilege ->{
                privileges.add("ROLE_"+privilege.getName());
        });
        return privileges;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        User userEntity= userRepository.findByEmail(email);
        if (null == userEntity) throw new UsernameNotFoundException(email);

        return this.modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDetails) {
        User user = userRepository.findByUserId(userDetails.getUserId());

        if(null == user) throw new UsernameNotFoundException("user id {"+userDetails.getUserId()+"} not found");
        if (userDetails.getUserId()!=null) user.setEmail(userDetails.getUserId());
        if(userDetails.getFirstName()!=null) user.setFirstName(userDetails.getFirstName());
        if(userDetails.getLastName()!=null) user.setLastName(userDetails.getLastName());
        if(userDetails.getEncryptedPassword()!=null) {
            user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getEncryptedPassword()));
        }
        if(null!=userDetails.getRoles()&& userDetails.getRoles().size()>0){
            List<Role> existingRoles = new ArrayList<Role>();
            userDetails.getRoles().forEach( role -> {
                    Role existingRole = roleRepository.findByName(role.getName());
                    existingRoles.add(existingRole);
            });
            user.setRoles(existingRoles);
        }
        userRepository.save(user);

        UserDto updatedUserDto = this.modelMapper.map(user,UserDto.class);
        return updatedUserDto;
    }
}
