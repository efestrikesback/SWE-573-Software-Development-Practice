package com.boun.devcom.service;

import com.boun.devcom.model.Profile;
import com.boun.devcom.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;


    public Profile updateProfile(Long userId, Profile profileDetails) {
        return profileRepository.findById(userId).map(profile -> {
            profile.setNickname(profileDetails.getNickname());
            profile.setBio(profileDetails.getBio());
            profile.setAvatarUrl(profileDetails.getAvatarUrl());
            return profileRepository.save(profile);
        }).orElseThrow(() -> new RuntimeException("Profile not found with id " + userId));
    }
}
