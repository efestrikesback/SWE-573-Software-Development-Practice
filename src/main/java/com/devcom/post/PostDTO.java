package com.devcom.post;
import lombok.Data;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private Long communityId;
    private Long templateId;
    private Long userId;
    private Set<PostDataDTO> postData;
}
