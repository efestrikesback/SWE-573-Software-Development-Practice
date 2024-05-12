package com.devcom.post;


import lombok.Data;
import java.util.List;

@Data
public class CreatePostRequest {
    private Long templateId;
    private String title;  // Optional, depends on template
    private List<PostDataDTO> data;
}
