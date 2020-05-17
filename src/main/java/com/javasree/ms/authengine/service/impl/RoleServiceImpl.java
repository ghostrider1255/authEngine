package com.javasree.ms.authengine.service.impl;

import com.javasree.ms.authengine.model.dto.RoleDto;
import com.javasree.ms.authengine.model.entity.Privilege;
import com.javasree.ms.authengine.model.entity.Role;
import com.javasree.ms.authengine.repository.PrivilegeRepository;
import com.javasree.ms.authengine.repository.RoleRepository;
import com.javasree.ms.authengine.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired private RoleRepository roleRepository;
    @Autowired private PrivilegeRepository privilegeRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = this.modelMapper.map(roleDto, Role.class);
        role.setPrivileges(refillPrivileges(role.getPrivileges()));
        roleRepository.save(role);
        return roleDto;
    }

    private Collection<Privilege> refillPrivileges(Collection<Privilege> privileges){
        Collection<Privilege> entityPrivileges = new ArrayList<Privilege>();
        privileges.stream().forEach( privileg -> {
                Privilege existingPrivilege = privilegeRepository.findByName(privileg.getName());
        if(null == existingPrivilege){
            Privilege newPrivilege = privilegeRepository.save(privileg);
            entityPrivileges.add(newPrivilege);
        } else{
            entityPrivileges.add(existingPrivilege);
        }
    });
        return entityPrivileges;
    }

    @Override
    public RoleDto createOrUpdate(RoleDto roleDto) {
        Role existingRole = roleRepository.findByName(roleDto.getName());
        if(null == existingRole){
            return createRole(roleDto);
        }
        else{
            ArrayList<Privilege> existingPrivileges = new ArrayList<Privilege>();
            roleDto.getPrivileges().forEach( privileDto -> {
                    existingPrivileges.add(privilegeRepository.findByName(privileDto.getName()));
            });
            existingRole.setPrivileges(existingPrivileges);
            this.roleRepository.save(existingRole);
            RoleDto updatedRoleDto = this.modelMapper.map(existingRole,RoleDto.class);
            return updatedRoleDto;
        }
    }

    @Override
    public RoleDto getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        RoleDto roleDto = modelMapper.map(role,RoleDto.class);
        return roleDto;
    }
}
