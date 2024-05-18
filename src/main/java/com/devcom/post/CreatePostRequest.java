package com.devcom.post;


import com.devcom.user.User;
import lombok.Data;
import java.util.List;

@Data
public class CreatePostRequest {
    private Long templateId;
    private String title;
    private User user;
    private List<PostDataRequest> data;
}