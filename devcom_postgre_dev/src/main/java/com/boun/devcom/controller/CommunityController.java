package com.boun.devcom.controller;

import com.boun.devcom.model.Community;
import com.boun.devcom.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<Community> createCommunity(@RequestBody Community community) {
        Community createdCommunity = communityService.createCommunity(community);
        return ResponseEntity.ok(createdCommunity);
    }

    @GetMapping
    public ResponseEntity<List<Community>> getAllCommunities() {
        List<Community> communities = communityService.listCommunities();
        return ResponseEntity.ok(communities);
    }

    @PutMapping("/{communityId}")
    public ResponseEntity<Community> updateCommunity(@PathVariable Long communityId, @RequestBody Community communityDetails) {
        Community updatedCommunity = communityService.updateCommunity(communityId, communityDetails);
        return ResponseEntity.ok(updatedCommunity);
    }
}
