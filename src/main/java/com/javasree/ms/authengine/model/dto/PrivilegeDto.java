package com.javasree.ms.authengine.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PrivilegeDto implements Serializable
{
    private static final long serialVersionUID = 42L;

    public PrivilegeDto(){}

    @NotNull(message = "Privilege name can not be null")
    @Size(min = 3,message = "privilege name can not be less than 3 char(s)")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
