package com.devcom.webapp.services;



import com.devcom.webapp.models.User;
import com.devcom.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User newUser) {
        // TODO add additional checks (e.g., for username or email uniqueness)
        return userRepository.save(newUser);
    }
}
