package com.syboks.tich_flightbooking.services;

import com.syboks.tich_flightbooking.entities.Booking;
import com.syboks.tich_flightbooking.entities.Payment;
import com.syboks.tich_flightbooking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingService bookingService;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment savePayment(Payment payment) {
        // Check if this payment already exists
        boolean isUpdate = payment.getId() != null && paymentRepository.existsById(payment.getId());

        // Save the payment
        Payment savedPayment = paymentRepository.save(payment);

        // Fetch the existing booking
        Booking booking = bookingService.getBookingById(savedPayment.getBooking().getId());

        if (!isUpdate) {
            // Only adjust totalPaid if this is a new payment
            double newTotalPaid = booking.getTotalPaid() + payment.getAmountPaid();


            if (newTotalPaid < booking.getPrice()) {
                booking.setPaymentStatus("Partially Paid");
            } else {
                booking.setPaymentStatus("Fully Paid");
            }
            booking.setTotalPaid(newTotalPaid);
            booking.setBalance(booking.getPrice() - booking.getTotalPaid());

            // Save the updated booking
            bookingService.saveBooking(booking);
        }

        return savedPayment;
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
