package com.example.AMS.Services;

import com.example.AMS.Entities.User;
import com.example.AMS.Repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    private UserRepo userRepo;

    public UserServices(UserRepo userRepo){
        this.userRepo = userRepo;
        userRepo.save(new User("admin", (long) 1.0, "admin"));
    }

    public User saveUser(User user){
        return this.userRepo.save(user);
    }

    public List<User> findAllUsers(){
        return this.userRepo.findAll();
    }

    public User findUserById(Long userID){
        return this.userRepo.findById(userID).orElse(null);
    }

    public User updateUser(User user){
        User userToUpdate = this.userRepo.findById(user.userId).orElse(null);
        if(userToUpdate == null) return null;
        else return saveUser(user);
    }

    public User deleteUser(Long userId){
        User user = this.userRepo.findById(userId).orElse(null);
            userRepo.deleteById(userId);
            return user;
    }
}
