package com.example.AMS.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    public Long roleId;
    public String name;

    public Role(){}

    public Role(Long roleId, String name){
        this.roleId = roleId;
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
