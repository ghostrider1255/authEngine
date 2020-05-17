package com.javasree.ms.authengine.service;

import com.javasree.ms.authengine.model.dto.PrivilegeDto;

public interface PrivilegeService {

    PrivilegeDto createPrivilege(PrivilegeDto privilegeDto);
    PrivilegeDto getPrivilegeByName(String name);
}
