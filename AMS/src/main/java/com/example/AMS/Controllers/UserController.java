package com.example.AMS.Controllers;

import com.example.AMS.Entities.User;
import com.example.AMS.Services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServices userServices;

    public static UserController instance;

    public UserController(UserServices userServices){
        this.userServices = userServices;
        instance = this;
    }

    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<User>(userServices.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable Long id){
        return new ResponseEntity<User>(userServices.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userServices.findAllUsers(), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return new ResponseEntity<User>(userServices.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        return new ResponseEntity<User>(userServices.deleteUser(id), HttpStatus.OK);
    }
}
