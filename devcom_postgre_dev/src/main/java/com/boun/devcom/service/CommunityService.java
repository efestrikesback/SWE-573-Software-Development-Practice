package com.boun.devcom.service;

import com.boun.devcom.model.Community;
import com.boun.devcom.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public Community createCommunity(Community community) {
        community.setArchived(false);  // Default to not archived when created
        return communityRepository.save(community);
    }

    public List<Community> listCommunities() {
        return communityRepository.findAll();
    }

    public Community updateCommunity(Long communityId, Community communityDetails) {
        return communityRepository.findById(communityId).map(community -> {
            community.setName(communityDetails.getName());
            community.setDescription(communityDetails.getDescription());
            community.setArchived(communityDetails.isArchived());
            return communityRepository.save(community);
        }).orElseThrow(() -> new RuntimeException("Community not found with id " + communityId));
    }
}