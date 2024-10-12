package com.syboks.tich_flightbooking.repository;

import com.syboks.tich_flightbooking.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

}
