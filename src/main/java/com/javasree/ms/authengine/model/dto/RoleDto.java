package com.javasree.ms.authengine.model.dto;

import java.io.Serializable;
import java.util.Collection;

public class RoleDto implements Serializable {

    private static final long serialVersionUID = 42L;

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
