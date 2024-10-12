package com.syboks.tich_flightbooking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate transactionDate;
    private double price;
    private double totalPaid;
    private double budget;
    private double actual;
    private double variance;
    private double margin;
    private double balance;
    private String departure;
    private String destination;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate departureDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate arrivalDate;
    private String visa;
    private String salesRep;
    private String bookingStatus;
    private String paymentStatus;
    private Integer numAdults;
    private Integer numChildren;
    private Integer numInfants;
    private String refNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Pkg pkg;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}