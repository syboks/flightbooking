package com.syboks.tich_flightbooking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private double price;
    private double actual;
    private LocalDate startDate;
    private LocalDate paymentDeadlineDate;
    private Integer numDays;
    private String platform;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
