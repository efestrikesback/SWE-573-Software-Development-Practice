package com.devcom.userProfile;


import com.devcom.user.User;
import com.devcom.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor

public class UserProfileService {

    private final UserProfileRepository repository;

    private final UserRepository userRepository;

    public UserProfile getCurrentProfile(){
        User profileOwner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id= profileOwner.getId();
//        return repository.findById(id).orElseThrow();
        return repository.findByUserId(id).orElseThrow(() -> new NoSuchElementException("Profile not found for user ID: " + id));
    }
    @Transactional
    public UserProfile createProfile(UserProfile profile) {

        User profileOwner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileOwnerId = profileOwner.getId();
        boolean profileExists = repository.existsByUserId(profileOwnerId);
        if (profileExists) {
            throw new IllegalStateException("Profile already exists for user ID: " + profileOwnerId);
        }
        profile.setUser(profileOwner);
        UserProfile createdProfile = repository.save(profile);
        return createdProfile;
    }


    @Transactional
    public UserProfile updateProfile(UserProfile updatedProfile) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileOwnerId = authenticatedUser.getId();
        return repository.findByUserId(profileOwnerId).map(profile -> {
            if(updatedProfile.getBio() != null) profile.setBio(updatedProfile.getBio());
            if(updatedProfile.getAvatarUrl() != null) profile.setAvatarUrl(updatedProfile.getAvatarUrl());
            return repository.save(profile);
        }).orElseThrow(() -> new IllegalArgumentException("No profile found for the logged-in user with ID: " + profileOwnerId));
    }


}
