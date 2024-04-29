package com.devcom.userProfile;

import com.devcom.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserProfile {

    @Id
    @GeneratedValue
    private Long profileId;
    private String bio;
    private String avatarUrl;

    //NOTE imported from devcom user !!! do not mix with security user!
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-profile")
    @JoinColumn(name = "user_id")
    private User user;

}
