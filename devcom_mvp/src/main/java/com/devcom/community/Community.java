package com.devcom.community;


import com.devcom.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(name = "is_private",nullable = false)
    private boolean isPrivate;
    private boolean isArchived;

    //The creator is the first owner
    //TODO ownership transfer
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "community",fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JsonManagedReference("community-members")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Membership> memberships;

    //TODO posts and data types


}
