package com.devcom.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static com.devcom.user.Permission.ADMIN_CREATE;
import static com.devcom.user.Permission.ADMIN_DELETE;
import static com.devcom.user.Permission.ADMIN_READ;
import static com.devcom.user.Permission.ADMIN_UPDATE;
import static com.devcom.user.Permission.MANAGER_CREATE;
import static com.devcom.user.Permission.MANAGER_DELETE;
import static com.devcom.user.Permission.MANAGER_READ;
import static com.devcom.user.Permission.MANAGER_UPDATE;
import static com.devcom.user.Role.ADMIN;
import static com.devcom.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    //TODO trim whitelist after context implementation
    //check js access
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/**",
            "/",
            "/index.html",
            "/css/**",
            "/js/**",
            "/mainPage",
            "/userProfile",
            "/userProfile/create",
            "/userProfile/currentProfile",
            "/communityPage",
            "/createCommunity",
            "/api/v1/community/**",
            "/api/v1/community/{communityId}/members",
            "/{communityId}/members",
            "/createPost",
            "/api/v1/templates/**",
            "/api/v1/community/*/createTemplate",
            "/api/v1/templates/*/addField",
            "/api/v1/community/*/createPost",
            "/api/v1/community/*/isOwner",
            "/{id}/posts",
            "/*/posts",
            "/api/v1/community/*/posts"

    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    //TODO: This is not safe, prepare for PROD allowedOrigins("http://localhost:3000")

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Allow frontend origin, adjust if different
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}


//                                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
//                                .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//                                .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//                                .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//                                .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
