package com.syboks.tich_flightbooking.repository;

import com.syboks.tich_flightbooking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
