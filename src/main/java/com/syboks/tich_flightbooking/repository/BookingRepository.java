package com.syboks.tich_flightbooking.repository;

import com.syboks.tich_flightbooking.dto.BookingProductDTO;
import com.syboks.tich_flightbooking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT new com.syboks.tich_flightbooking.dto.BookingProductDTO( c.firstName, c.lastName, p.name, pr.productName, pr.price, pr.paymentDeadlineDate, pr.actual, b.id) " +
            "FROM Booking b " +
            "JOIN b.customer c " +
            "JOIN b.pkg p " +
            "JOIN b.products pr " +
            "WHERE pr.actual = 0 AND pr.paymentDeadlineDate < CURRENT_DATE " +
            "ORDER BY p.name ASC")
    List<BookingProductDTO> findOverdueProducts();
}
