package com.javasree.ms.authengine.model.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name", nullable = false, length = 50)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "RolesPrivileges",
            joinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegeId", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

}
