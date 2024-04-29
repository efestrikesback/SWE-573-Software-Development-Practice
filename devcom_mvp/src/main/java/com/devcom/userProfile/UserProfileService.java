package com.devcom.userProfile;


import com.devcom.user.User;
import com.devcom.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class UserProfileService {

    private final UserProfileRepository repository;

    private final UserRepository userRepository;


    @Transactional
    public UserProfile createProfile(UserProfile profile) {

        User profileOwner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profile.setUser(profileOwner);
        UserProfile createdProfile = repository.save(profile);
        Long profileOwnerId = profileOwner.getId();
        // Check if a profile already exists for the user to prevent duplicates
        boolean profileExists = repository.existsById(profileOwnerId);
        if (profileExists) {
            throw new IllegalStateException("Profile already exists for user ID: " + profileOwnerId);
        }
        return createdProfile;
    }


}
