package com.syboks.tich_flightbooking.repository;

import com.syboks.tich_flightbooking.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
