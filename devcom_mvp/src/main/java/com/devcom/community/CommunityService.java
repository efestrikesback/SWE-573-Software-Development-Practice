package com.devcom.community;


import com.devcom.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MembershipRepository membershipRepository;

    public Community getCommunity(Long id){
        return communityRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Community createCommunity(Community community){
        User owner= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        community.setOwner(owner);
        Community createdCommunity=communityRepository.save(community);
        MembershipCode subsKey= new MembershipCode(owner.getId(), createdCommunity.getCommunityId());
        membershipRepository.save(new Membership(subsKey,owner,createdCommunity));
        return createdCommunity;
    }


}
