package com.devcom.auth;

import com.devcom.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String firstname;
  private String lastname;
  private String username;
  private String email;
  private String password;
  //TODO: set default role as USER!
  @Builder.Default
  private Role role = Role.USER;
}
