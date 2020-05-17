package com.javasree.ms.authengine.model.dto;

import java.util.Collection;

public class RoleDto {

    private String name;

    private Collection<PrivilegeDto> privileges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<PrivilegeDto> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<PrivilegeDto> privileges) {
        this.privileges = privileges;
    }
}
