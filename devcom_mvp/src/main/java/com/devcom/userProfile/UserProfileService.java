package com.devcom.userProfile;


import com.devcom.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserProfileService {

    private final UserProfileRepository repository;

    @Transactional
    public UserProfile updateProfile(UserProfile profileDetails) {
        User profileOwner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = profileOwner.getId();

        // Check if profile already exists
        Optional<UserProfile> existingProfile = repository.findById(userId);

        UserProfile profile = existingProfile.orElseGet(() -> new UserProfile());
        profile.setUser(profileOwner); // Assuming UserProfile has a 'user' field for the relationship

        // Update fields from profileDetails
        profile.setNickname(profileDetails.getNickname());
        profile.setBio(profileDetails.getBio());
        profile.setAvatarUrl(profileDetails.getAvatarUrl());
        // ... other fields

        // Save will update if profile exists, or insert if it does not
        return repository.save(profile);
    }

//    @Transactional
//    public UserProfile updateProfile(UserProfile profileDetails) {
//        User profileOwner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Integer profileOwnerId = profileOwner.getId();
//
//        // Find the existing profile or create a new one if it doesn't exist
//        UserProfile profile = repository.findById(profileOwnerId).orElseGet(() -> {
//            UserProfile newProfile = new UserProfile();
//            newProfile.setUser(profileOwner); // Set the relationship with User
//            return newProfile;
//        });
//
//        profile.setNickname(profileDetails.getNickname());
//        profile.setBio(profileDetails.getBio());
//        profile.setAvatarUrl(profileDetails.getAvatarUrl());
//        return repository.save(profile);
//    }

//    @Transactional
//    public UserProfile updateProfile(UserProfile profileDetails) {
//
//        User profileOwner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        Integer profileOwnerId = profileOwner.getId();
//
//        return repository.findById(profileOwnerId).map(UserProfile -> {
//
//            profileDetails.setNickname(profileDetails.getNickname());
//            profileDetails.setBio(profileDetails.getBio());
//            profileDetails.setAvatarUrl(profileDetails.getAvatarUrl());
//            return repository.save(profileDetails);
//        }).orElseThrow(() -> new RuntimeException("Profile not found with id " + profileOwnerId));
//    }


}
