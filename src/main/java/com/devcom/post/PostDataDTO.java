package com.devcom.post;
import lombok.Data;

@Data
public class PostDataDTO {
    private Long id;
    private Long postId;
    private Long fieldId;
    private String value;
}
