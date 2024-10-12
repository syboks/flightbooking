package com.syboks.tich_flightbooking.entities;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pkg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;



    @ManyToMany
    @JoinTable(
            name = "package_inclusion",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "inclusion_id")
    )
    private List<Inclusion> inclusions;

}
