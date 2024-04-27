package com.devcom.community;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
//    @PostMapping("/create")
//    public ResponseEntity<Community> createCommunity(@RequestBody Community community){
//
//        return ResponseEntity.ok(communityService.createCommunity(community));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Community> getCommunity (@PathVariable Long id){
//        return ResponseEntity.ok(communityService.getCommunity(id));
//    }
//
//    @PostMapping("/subscribe/{id}")
//    public ResponseEntity<Subscription> subscribeToCommunity(@PathVariable Long id){
//        return ResponseEntity.ok(communityService.subscribeToCommunity(id));
//    }
}
