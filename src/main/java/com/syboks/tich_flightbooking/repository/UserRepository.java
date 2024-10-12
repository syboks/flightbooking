package com.syboks.tich_flightbooking.repository;

import com.syboks.tich_flightbooking.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}
