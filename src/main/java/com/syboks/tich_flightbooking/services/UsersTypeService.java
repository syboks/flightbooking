package com.syboks.tich_flightbooking.services;


import com.syboks.tich_flightbooking.entities.UsersType;
import com.syboks.tich_flightbooking.repository.UsersTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {
    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }
}
