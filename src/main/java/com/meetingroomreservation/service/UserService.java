package com.meetingroomreservation.service;


import java.util.List;
import java.util.Optional;

import com.meetingroomreservation.dto.UserDto;
import com.meetingroomreservation.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);
    
    Optional<User> findById(Long id);

    List<UserDto> findAllUsers();
}
