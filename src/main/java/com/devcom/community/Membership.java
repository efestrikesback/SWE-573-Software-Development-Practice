package com.devcom.community;


import com.devcom.user.CommunityRole;
import com.devcom.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Membership {

    @EmbeddedId
    private MembershipCode id;

    private String communityRole;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-memberships")
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne
    @MapsId("communityId")
    @JoinColumn(name = "community_id")
    @JsonBackReference("community-members")
    @EqualsAndHashCode.Exclude
    private Community community;




}
