package com.devcom.userProfile;

import com.devcom.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUserId(Long userId);

    Optional<UserProfile> findByUserId(Long userId);

}
