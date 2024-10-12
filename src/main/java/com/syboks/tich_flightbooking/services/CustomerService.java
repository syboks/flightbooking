package com.syboks.tich_flightbooking.services;

import com.syboks.tich_flightbooking.entities.Customer;
import com.syboks.tich_flightbooking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
