package com.digital.api.digital_booking.db;


import com.digital.api.digital_booking.models.Role;
import com.digital.api.digital_booking.models.Users;

import com.digital.api.digital_booking.repositories.IUserRepository;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//@Service
@AllArgsConstructor
public class DBInit implements CommandLineRunner {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        userRepository.save(Users.builder()
                .email("admin@db.com")
                .name("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build());
    }
}
