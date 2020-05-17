package com.javasree.ms.authengine.repository;

import com.javasree.ms.authengine.model.entity.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege,Long> {
    public Privilege findByName(String name);
}
