package com.meetingroomreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
