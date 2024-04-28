package com.devcom.config;

import com.devcom.token.TokenRepository;
import com.devcom.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final TokenRepository tokenRepository;


  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
//    if (request.getServletPath().contains("/api/v1/auth")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.extractEmail(jwt);
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      logger.info("Entered userDetails check");
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//      logger.info("User Details are: "+userDetails.toString());
      logger.info("Is Token Valid: "+jwtService.isTokenValid(jwt,(User) userDetails));
      var isTokenValid = tokenRepository.findByToken(jwt)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);
      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        logger.info("Auth Object is: "+SecurityContextHolder.getContext().getAuthentication().toString());
      }
    }
    filterChain.doFilter(request, response);
  }
}
