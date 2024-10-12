package com.syboks.tich_flightbooking.services;


import com.syboks.tich_flightbooking.entities.Users;
import com.syboks.tich_flightbooking.repository.UsersRepository;
import com.syboks.tich_flightbooking.util.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=  usersRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Could not find user"));
        return new CustomUserDetails(user);
    }
}
