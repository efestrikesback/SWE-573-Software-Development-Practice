package com.devcom.post;

import lombok.Data;

@Data
public class PostDataDTO {
    private Long fieldId;   // Identifier for the template field
    private String value;   // Value to be stored in this field
}
