package com.example.AMS.Services;

import com.example.AMS.Entities.Role;
import com.example.AMS.Repositories.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServices {
    private RoleRepo roleRepo;

    public RoleServices(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    public Role saveRole(Role role){
        return this.roleRepo.save(role);
    }

    public List<Role> getAllRoles(){
        return this.roleRepo.findAll();
    }

    public Role getRoleById(Long id){
        return roleRepo.findById(id).orElse(null);
    }

    public Role updateRole(Role role){
        Role roleToUpdate = roleRepo.findById(role.roleId).orElse(null);
        if (roleToUpdate == null) return null;
        else return roleRepo.save(role);
    }

    public Role deleteRole(Long id){
        Role roleToDelete = roleRepo.findById(id).orElse(null);
        roleRepo.deleteById(id);
        return roleToDelete;
    }
}
