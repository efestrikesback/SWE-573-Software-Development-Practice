package com.boun.devcom.service;

import com.boun.devcom.model.User;
import com.boun.devcom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUser(User user) {
        // Here you would add logic to hash the password, check for existing users, etc.
        return userRepository.save(user);
    }

    // Additional methods for user authentication, profile updates, etc. can be added here
}
