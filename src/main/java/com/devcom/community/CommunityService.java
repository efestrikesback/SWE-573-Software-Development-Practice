package com.devcom.community;


import com.devcom.config.JwtService;
import com.devcom.post.*;
import com.devcom.user.CommunityRole;
import com.devcom.user.User;
import com.devcom.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final MembershipRepository membershipRepository;
    private final TemplateRepository templateRepository;
    private final TemplateFieldRepository templateFieldRepository;
    private final PostRepository postRepository;
    private final PostDataRepository postDataRepository;
    public Logger logger = LoggerFactory.getLogger(JwtService.class);

    public Community getCommunity(Long id) {
        return communityRepository.findById(id).orElseThrow();
    }

    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    @Transactional
    public Community createCommunity(Community community) {
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        community.setOwner(owner);
        String communityRole = CommunityRole.CREATOR.toString();
        Community createdCommunity = communityRepository.save(community);
        MembershipCode membershipCode = new MembershipCode(owner.getId(), createdCommunity.getCommunityId());
        membershipRepository.save(new Membership(membershipCode, communityRole, owner, createdCommunity));
        return createdCommunity;
    }

    @Transactional
    public Membership joinCommunity(Long id) {
        User joiner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Community community = communityRepository.findById(id).orElseThrow();

        // Check if the user is already a member
        MembershipCode membershipCode = new MembershipCode(joiner.getId(), community.getCommunityId());
        if (membershipRepository.existsById(membershipCode)) {
            throw new IllegalStateException("User is already a member of the community");
        }

        // Check if the user is the creator
        if (community.getOwner().getId().equals(joiner.getId())) {
            throw new IllegalStateException("User is the creator of the community and already a member");
        }

        String communityRole = CommunityRole.MEMBER.toString();
        return membershipRepository.save(new Membership(membershipCode, communityRole, joiner, community));
    }

    @Transactional
    public void leaveCommunity(Long id) {
        User leaver = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MembershipCode membershipCode = new MembershipCode(leaver.getId(), id);
        Membership membership = membershipRepository.findById(membershipCode).orElseThrow();
        membershipRepository.delete(membership);
    }

    public boolean isMember(Long communityId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MembershipCode membershipCode = new MembershipCode(user.getId(), communityId);
        return membershipRepository.existsById(membershipCode);
    }

    public boolean isOwner(Long communityId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        return community.getOwner().getId().equals(user.getId());
    }

    @Transactional(readOnly = true)
    public List<String> getCommunityMembers(Long communityId) {
        List<Membership> memberships = membershipRepository.findByCommunity_CommunityId(communityId);
        List<String> memberUsernames = memberships.stream()
                .map(membership -> membership.getUser().getUsername())
                .collect(Collectors.toList());

        // Log the usernames to the console
        logger.info("Community member usernames for community ID {}: {}", communityId, memberUsernames);

        return memberUsernames;
    }


    @Transactional
    public Template createTemplate(Long communityId, Template template) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        template.setCommunity(community);
        return templateRepository.save(template);
    }

    @Transactional
    public TemplateField addFieldToTemplate(Long communityId, Long templateId, TemplateField field) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        field.setTemplate(template);
        return templateFieldRepository.save(field);
    }

    @Transactional
    public Post createPost(Long communityId, CreatePostRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        Template template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(() -> new RuntimeException("Template not found"));

        Post post = new Post();
        post.setCommunity(community);
        post.setTemplate(template);
        post.setUser(user);
        post.setTitle(request.getTitle());
        Post savedPost = postRepository.save(post);

        for (PostDataRequest postDataRequest : request.getData()) {
            TemplateField field = templateFieldRepository.findById(postDataRequest.getFieldId())
                    .orElseThrow(() -> new RuntimeException("Field not found"));
            PostData postData = new PostData();
            postData.setPost(savedPost);
            postData.setTemplateField(field);
            postData.setValue(postDataRequest.getValue());
            postDataRepository.save(postData);
        }

        return savedPost;
    }

    public List<Template> getTemplates(Long communityId) {
        return templateRepository.findByCommunityCommunityId(communityId);
    }

}
