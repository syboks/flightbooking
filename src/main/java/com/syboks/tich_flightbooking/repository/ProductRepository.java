package com.syboks.tich_flightbooking.repository;

import com.syboks.tich_flightbooking.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}