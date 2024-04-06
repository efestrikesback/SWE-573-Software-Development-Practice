package com.devcom.webapp.repositories;


import com.devcom.webapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
