package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.entities.Customer;
import com.syboks.tich_flightbooking.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String redirectToCustomers() {
        return "redirect:/customers";
    }

    @GetMapping
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        //model.addObject("cacheControl", CacheControl.noStore().cachePrivate());
        return "customer-list";  // Returns a Thymeleaf template named "customer-list.html"
    }

    @GetMapping("/new")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";  // Returns a Thymeleaf template named "customer-form.html"
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers";  // Redirects to the list of customers
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getCustomerById(id).get();
        model.addAttribute("customer", customer);
        return "customer-form";  // Reuses the form template for editing
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    @GetMapping("/details/{id}")
    public String showCustomerDetails(@PathVariable("id") Long id, Model model) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            model.addAttribute("bookings", customer.get().getBookings());
            return "view";
        } else {
            return "redirect:/customers";
        }
       
    }



}
