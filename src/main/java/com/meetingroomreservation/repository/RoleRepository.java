package com.meetingroomreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
