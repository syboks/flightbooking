package com.syboks.tich_flightbooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

@Getter
@Setter
public class BookingProductDTO {

    private String firstName;
    private String lastName;
    private String packageName;
    private String productName;
    private double price;
    private LocalDate paymentDeadlineDate;
    private double actual;
    private Long bookingId;

    public BookingProductDTO(String firstName, String lastName,
                             String packageName, String productName, double price,
                             LocalDate paymentDeadlineDate, double actual, Long bookingId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.packageName = packageName;
        this.productName = productName;
        this.price = price;
        this.paymentDeadlineDate = paymentDeadlineDate;
        this.actual = actual;
        this.bookingId=bookingId;
    }

    public String getFormattedPrice() {
        Locale southAfrica = new Locale("en", "ZA");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(southAfrica);
        return currencyFormatter.format(price);
    }

}
