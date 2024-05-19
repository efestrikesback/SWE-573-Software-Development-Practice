package com.devcom.post;


import com.devcom.community.Community;
import com.devcom.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title; // Optional

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "community_id",nullable = false)
    @JsonBackReference("community-posts")
    private Community community;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"email","firstname","lastname","password"})
    private User user;


    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    @JsonManagedReference("post-fields")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<PostData> postData;

}
