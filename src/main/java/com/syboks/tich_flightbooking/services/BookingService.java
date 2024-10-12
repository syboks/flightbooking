package com.syboks.tich_flightbooking.services;

import com.syboks.tich_flightbooking.dto.BookingProductDTO;
import com.syboks.tich_flightbooking.entities.Booking;
import com.syboks.tich_flightbooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
    public List<BookingProductDTO> getOverdueProducts() {
        return bookingRepository.findOverdueProducts();
    }
}
