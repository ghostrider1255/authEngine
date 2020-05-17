package com.javasree.ms.authengine.repository;

import com.javasree.ms.authengine.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);
    User findByUserId(String userId);
}

