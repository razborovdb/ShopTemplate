package com.funcoding.shoptemplate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shop_role")
public class Role {
    @Id
    @Column(name = "role")
    private String roleName;
    @Column(name = "role_description")
    private String roleDescription;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "roles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Role() {

    }

    public Role(String roleName, String roleDescription, Set<User> users) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.users = users;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", users=" + users +
                '}';
    }
}
