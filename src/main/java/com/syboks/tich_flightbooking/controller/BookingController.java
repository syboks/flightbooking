package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.dto.BookingProductDTO;
import com.syboks.tich_flightbooking.entities.*;
import com.syboks.tich_flightbooking.repository.ItemRepository;
import com.syboks.tich_flightbooking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PkgService pkgService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "booking-list";  // Returns a Thymeleaf template named "booking-list.html"
    }
    @GetMapping("/overdue-products")
    public String showOverdueProducts(Model model) {
        List<BookingProductDTO> overdueProducts = bookingService.getOverdueProducts();
        model.addAttribute("overdueProducts", overdueProducts);
        return "overdue-products";
    }

    @GetMapping("/packages/{id}")
    @ResponseBody
    public Pkg getPkg(@PathVariable Long id) {
        return pkgService.getPackageById(id);
    }

    @GetMapping("/new")
    public String showBookingForm(@RequestParam(value = "customerId", required = false) Long customerId, Model model) {
        Booking booking = new Booking();

        booking.getProducts().add(new Product()); // Add an initial product row
        //model.addAttribute("booking", booking);

        if (customerId != null) {
            Customer customer = customerService.getCustomerById(customerId).orElse(null);
            model.addAttribute("customer", customer);
            booking.setCustomer(customer);  // Set the customer details in the booking
        }

//        List<Customer> customers = customerService.getAllCustomers();
        List<Pkg> packages = pkgService.getAllPackages();
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("booking", booking);
        //model.addAttribute("customer", customer);
        model.addAttribute("packages", packages);

        return "booking-form";  // Returns a Thymeleaf template named "booking-form.html"
    }

    @PostMapping("/items/add")
    public String addItem(@ModelAttribute Product product, Model model) {
        itemService.save(product);  // Save the new item to the database
        return "redirect:/new";  // Redirect back to the form page
    }

    @PostMapping("/save")
    public String saveBooking(@ModelAttribute("booking") Booking booking) {

        Customer customer=customerService.getCustomerById(booking.getCustomer().getId()).get();
        booking.setCustomer(customer);
        booking.setBalance(booking.getPrice()-booking.getTotalPaid());

        for (Product product : booking.getProducts()) {
            product.setBooking(booking);
        }

        bookingService.saveBooking(booking);
        //return "redirect:/bookings";  // Redirects to the list of bookings
        return "redirect:/bookings/details/" + booking.getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        List<Customer> customers = customerService.getAllCustomers();
        List<Pkg> packages = pkgService.getAllPackages();

        if (booking != null) {
            model.addAttribute("booking", booking);
            model.addAttribute("customer", booking.getCustomer());
        }
        model.addAttribute("booking", booking);
        model.addAttribute("customers", customers);
        model.addAttribute("packages", packages);
        return "booking-form";  // Reuses the form template for editing
    }

    @GetMapping("/details/{id}")
    public String showBookingDetails(@PathVariable("id") Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            // Handle the case where the booking is not found
            // You can redirect to an error page or the bookings list with an error message
            return "redirect:/bookings?error=BookingNotFound";
        }
        model.addAttribute("booking", booking);
        model.addAttribute("products",booking.getProducts());
        return "booking-details"; // This is the name of the Thymeleaf template
    }


    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable("id") Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings";
    }
    @GetMapping("/makePayment")
    public String showPaymentForm(@RequestParam("id") Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        Payment payment = new Payment();
        payment.setBooking(booking);

        model.addAttribute("payment", payment);
        return "payment-form";  // Returns a Thymeleaf template named "payment-form.html"
    }

    @PostMapping("/payments/save")
    public String savePayment(@ModelAttribute("payment") Payment payment) {
        paymentService.savePayment(payment);  // Save the payment
        //return "redirect:/bookings";  // Redirect to the list of bookings
        return "redirect:/bookings/details/" + payment.getBooking().getId();
    }

    @GetMapping("/products/edit/{productId}")
    public String showEditProductForm(@PathVariable("productId") Long productId, Model model) {
        Product product = itemService.findById(productId);
        if (product != null) {
            model.addAttribute("product", product);
            return ""; // Returns the Thymeleaf template for editing a product
        } else {
            // Handle the case where the product is not found
            return "redirect:/bookings?error=ProductNotFound";
        }
    }

    // Method to handle the form submission for updating a product
    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute("product") Product product) {
        itemService.save(product); // Save the updated product
        return "redirect:/bookings/details/" + product.getBooking().getId(); // Redirect to the booking details
    }


}
