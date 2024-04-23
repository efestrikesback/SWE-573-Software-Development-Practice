package com.boun.devcom.controller;

import com.boun.devcom.model.Profile;
import com.boun.devcom.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(@PathVariable Long userId, @RequestBody Profile profile) {
        Profile updatedProfile = profileService.updateProfile(userId, profile);
        return ResponseEntity.ok(updatedProfile);
    }
}
