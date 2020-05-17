package com.javasree.ms.authengine.repository;

import com.javasree.ms.authengine.model.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    public Role findByName(String name);
}
