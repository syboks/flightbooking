package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.entities.Booking;
import com.syboks.tich_flightbooking.entities.Payment;
import com.syboks.tich_flightbooking.services.BookingService;
import com.syboks.tich_flightbooking.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String listPayments(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment-list";  // Returns a Thymeleaf template named "payment-list.html"
    }

    @GetMapping("/new")
    public String showPaymentForm(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("payment", new Payment());
        model.addAttribute("bookings", bookings);
        return "payment-form";  // Returns a Thymeleaf template named "payment-form.html"
    }

    @PostMapping
    public String savePayment(@ModelAttribute("payment") Payment payment) {
        paymentService.savePayment(payment);
        //return "redirect:/payments";  // Redirects to the list of payments

        Long bookingId = payment.getBooking().getId();

        // Redirect to the booking details page
        return "redirect:/bookings/details/" + bookingId;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Payment payment = paymentService.getPaymentById(id);
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("payment", payment);
        model.addAttribute("bookings", bookings);
        return "payment-form";  // Reuses the form template for editing
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable("id") Long id) {
        paymentService.deletePayment(id);
        return "redirect:/payments";
    }
}
