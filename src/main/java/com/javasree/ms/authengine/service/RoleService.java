package com.javasree.ms.authengine.service;

import com.javasree.ms.authengine.model.dto.RoleDto;

public interface RoleService {
    public RoleDto createRole(RoleDto roleDto);
    public RoleDto createOrUpdate(RoleDto roleDto);
    public RoleDto getRoleByName(String name);
}
