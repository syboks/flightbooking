package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.entities.Booking;
import com.syboks.tich_flightbooking.entities.Product;
import com.syboks.tich_flightbooking.repository.BookingRepository;
import com.syboks.tich_flightbooking.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BookingRepository bookingRepository;

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/bookings?error=ProductNotFound";
        }
        model.addAttribute("product", product);
        return "edit-product-modal :: #editProductModal"; // Thymeleaf template for editing a product
    }

    // Handle update
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        Product existingProduct = productService.findById(product.getId());
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setActual(product.getActual());
            existingProduct.setStartDate(product.getStartDate());
            existingProduct.setPaymentDeadlineDate(product.getPaymentDeadlineDate());
            existingProduct.setNumDays(product.getNumDays());
            existingProduct.setPlatform(product.getPlatform());
            productService.save(existingProduct);
        }
        redirectAttributes.addFlashAttribute("message", "Product updated successfully");
        return "redirect:/bookings/details/" + product.getBooking().getId();
    }

    @PostMapping("/updateProductActual")
    public String updateProductActual(@ModelAttribute Product product) {

        Product existingProduct = productService.findById(product.getId());
        /*if (existingProduct != null) {
            existingProduct.setActual(product.getActual());
            productService.save(existingProduct);
        }*/

        Booking booking = existingProduct.getBooking();
        if (booking != null) {
            // Subtract the old actual value from Booking
            booking.setActual(booking.getActual() - existingProduct.getActual());

            // Update the actual field of the Product
            existingProduct.setActual(product.getActual());

            // Add the new actual value from the Product to Booking
            booking.setActual(booking.getActual() + product.getActual());
            booking.setVariance(booking.getBudget() - booking.getActual());

            // Calculate and update margin (price - actual)
            booking.setMargin(booking.getPrice() - booking.getActual());
            booking.setBalance(booking.getPrice()-booking.getTotalPaid());

            // Save the updated Product and Booking entities
            productService.save(existingProduct);
            bookingRepository.save(booking);
            return "redirect:/bookings/details/" + booking.getId();
        } else {
            // Handle the case where Booking is null
            return "redirect:/bookings?error=BookingNotFound";
        }
    }

    // Handle delete
    /*@GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(id);
        if (product != null) {
            Long bookingId = product.getBooking().getId();
            productService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Product deleted successfully");
            return "redirect:/bookings/details/" + bookingId;
        }
        redirectAttributes.addFlashAttribute("error", "Product not found");
        return "redirect:/bookings";
    }*/

    // Save new product
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Product saved successfully");
        return "redirect:/bookings/details/" + product.getBooking().getId();
    }
}
