package com.javasree.ms.authengine.service.impl;

import com.javasree.ms.authengine.model.dto.PrivilegeDto;
import com.javasree.ms.authengine.model.entity.Privilege;
import com.javasree.ms.authengine.repository.PrivilegeRepository;
import com.javasree.ms.authengine.service.PrivilegeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired private PrivilegeRepository privilegeRepository;
    @Autowired private ModelMapper modelMapper;

    public PrivilegeServiceImpl(){

    }

    @Override
    public PrivilegeDto createPrivilege(PrivilegeDto privilegeDto) {
        Privilege privilege = this.modelMapper.map(privilegeDto, Privilege.class);
        privilegeRepository.save(privilege);
        return privilegeDto;
    }

    @Override
    public PrivilegeDto getPrivilegeByName(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        PrivilegeDto privilegeDto = this.modelMapper.map(privilege,PrivilegeDto.class);
        return privilegeDto;
    }
}
