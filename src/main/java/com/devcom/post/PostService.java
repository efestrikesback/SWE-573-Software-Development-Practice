package com.devcom.post;

import com.devcom.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    public List<PostDTO> getPostsByCommunityId(Long communityId) {
        List<Post> posts = postRepository.findByCommunityCommunityId(communityId);
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setCommunityId(post.getCommunity().getCommunityId());
        postDTO.setTemplateId(post.getTemplate().getId());
        postDTO.setUserId(post.getUser().getId());

        Set<PostDataDTO> postDataDTOs = post.getPostData().stream().map(postData -> {
            PostDataDTO postDataDTO = new PostDataDTO();
            postDataDTO.setId(postData.getId());
            postDataDTO.setPostId(postData.getPost().getId());
            postDataDTO.setFieldId(postData.getTemplateField().getId());
            postDataDTO.setValue(postData.getValue());
            return postDataDTO;
        }).collect(Collectors.toSet());

        postDTO.setPostData(postDataDTOs);

        return postDTO;
    }


}
