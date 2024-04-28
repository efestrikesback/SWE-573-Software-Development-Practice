package com.devcom.user;

import com.devcom.community.Membership;
import com.devcom.token.Token;

import com.devcom.userProfile.UserProfile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Long id;
  private String firstname;
  private String lastname;
  @Column(nullable = false,unique = true)
  private String username;
  @Column(nullable = false,unique = true)
  private String email;
  @Column(nullable = false)
  private String password;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  private UserProfile profile;

  @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
  @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
  @JsonManagedReference("user-memberships")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<Membership> memberships;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  @ToString.Exclude

  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
