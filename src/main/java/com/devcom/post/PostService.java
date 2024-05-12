package com.devcom.post;

import com.devcom.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostDataRepository postDataRepository;
    private final TemplateRepository templateRepository;

    //TODO

//    @Transactional
//    public Post createPost(CreatePostRequest request) {
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Template template = templateRepository.findById(request.getTemplateId())
//                .orElseThrow(() -> new IllegalArgumentException("Template not found"));
//
//        Post post = Post.builder()
//                .title(request.getTitle())
//                .community(template.getCommunity())
//                .template(template)
//                .user(user)
//                .build();
//        Post savedPost = postRepository.save(post);
//
//        request.getData().forEach(data -> {
//
//            TemplateField field = template.getFields().stream()
//                    .filter(f -> f.getId().equals(data.getFieldId()))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("Field not found in template: " + data.getFieldId()));
//            PostData postData = PostData.builder()
//                    .post(savedPost)
//                    .templateField(field)
//                    .value(data.getValue())
//                    .build();
//            postDataRepository.save(postData);
//        });
//        return post;
//    }

    @Transactional
    public Post createPost(CreatePostRequest request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Template template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));

        Post post = Post.builder()
                .title(request.getTitle())
                .community(template.getCommunity())
                .template(template)
                .user(user)
                .build();
        return postRepository.save(post);
    }
}
