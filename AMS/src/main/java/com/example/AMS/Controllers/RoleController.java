package com.example.AMS.Controllers;

import com.example.AMS.Entities.Role;
import com.example.AMS.Services.RoleServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleServices roleServices;

    public RoleController(RoleServices roleServices){
        this.roleServices = roleServices;
    }

    @PostMapping("")
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        return new ResponseEntity<Role>(roleServices.saveRole(role), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId){
        return new ResponseEntity<Role>(roleServices.getRoleById(roleId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<List<Role>>(roleServices.getAllRoles(), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Role> updateRole(@RequestBody Role role){
        return new ResponseEntity<Role>(roleServices.updateRole(role), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Role> deleteRole(@PathVariable Long roleId){
        return new ResponseEntity<Role>(roleServices.deleteRole(roleId), HttpStatus.OK);
    }
}
