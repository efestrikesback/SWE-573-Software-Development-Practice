package com.devcom.community;

import com.devcom.post.Post;
import com.devcom.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/create")
    public ResponseEntity<Community> createCommunity(@RequestBody Community community) {
        return ResponseEntity.ok(communityService.createCommunity(community));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Community> getCommunity(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.getCommunity(id));
    }

    @GetMapping
    public ResponseEntity<List<Community>> getAllCommunities() {
        return ResponseEntity.ok(communityService.getAllCommunities());
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<Membership> joinCommunity(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.joinCommunity(id));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Community> getCommunityDetails(@PathVariable Long id) {
        Community community = communityService.getCommunity(id);
        return ResponseEntity.ok(community);
    }


}
