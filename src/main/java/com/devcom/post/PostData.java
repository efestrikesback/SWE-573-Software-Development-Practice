package com.devcom.post;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_data")
public class PostData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnoreProperties("postData")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "template_field_id", nullable = false)
    @JsonIgnoreProperties("postData")
    private TemplateField templateField;

    @Column(nullable = false)
    private String value;
}
