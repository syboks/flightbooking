package com.syboks.tich_flightbooking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ref;
    private LocalDate transactionDate;
    private double amountPaid;
    private String receiptNo;
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
