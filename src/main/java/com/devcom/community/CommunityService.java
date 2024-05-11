package com.devcom.community;


import com.devcom.user.CommunityRole;
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
        String communityRole= CommunityRole.CREATOR.toString();
        Community createdCommunity=communityRepository.save(community);
        MembershipCode membershipCode= new MembershipCode(owner.getId(), createdCommunity.getCommunityId());
        membershipRepository.save(new Membership(membershipCode,communityRole,owner,createdCommunity));
        return createdCommunity;
    }

    @Transactional
    public Membership joinCommunity(Long id){
        User joiner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String communityRole= CommunityRole.MEMBER.toString();
        Community community = communityRepository.findById(id).orElseThrow();
        MembershipCode membershipCode = new MembershipCode(joiner.getId(), community.getCommunityId());
        return membershipRepository.save(new Membership(membershipCode,communityRole,joiner,community));
    }


}
