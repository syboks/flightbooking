package com.syboks.tich_flightbooking.repository;

import com.syboks.tich_flightbooking.entities.Item;
import com.syboks.tich_flightbooking.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Product, Long> {
    // Additional query methods (if needed) can be defined here
}
