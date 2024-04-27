package com.devcom.userProfile;


import com.devcom.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class UserProfileService {

    private final UserProfileRepository repository;



    @Transactional
    public UserProfile updateProfile(UserProfile profileDetails) {
        
        User profileOwner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer profileOwnerId = profileOwner.getId();

        return repository.findById(profileOwnerId).map(UserProfile -> {

            profileDetails.setNickname(profileDetails.getNickname());
            profileDetails.setBio(profileDetails.getBio());
            profileDetails.setAvatarUrl(profileDetails.getAvatarUrl());
            return repository.save(profileDetails);
        }).orElseThrow(() -> new RuntimeException("Profile not found with id " + profileOwnerId));
    }



}
