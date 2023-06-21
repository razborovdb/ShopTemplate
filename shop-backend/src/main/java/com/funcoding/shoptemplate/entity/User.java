package com.funcoding.shoptemplate.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shop_user")
public class User {

    @Id
    @Column(name = "name")
    private String userName;
    @Column(name = "user_first_name")
    private String userFirstName;
    @Column(name = "user_last_name")
    private String userLastName;
    @Column(name = "user_password")
    private String userPassword;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roles = new HashSet<>();

    public User( ) {

    }

    public User(String userName, String userFirstName, String userLastName, String userPassword, Set<Role> roles) {
        this.userName = userName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPassword = userPassword;
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(String roleId) {
        Role role = this.roles.stream().filter(t -> t.getRoleName() == roleId).findFirst().orElse(null);
        if (role != null) {
            this.roles.remove(role);
            role.getUsers().remove(this);
        }
    }

    @Override
    public String toString() {
        String setRole = "";
        for(Role s: roles) {
            setRole += " " + s.getRoleName();
        }
        return "User{" +
                "userName='" + userName + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", roles=" + setRole +
                '}';
    }
}
