package com.syboks.tich_flightbooking.services;

import com.syboks.tich_flightbooking.entities.Inclusion;
import com.syboks.tich_flightbooking.repository.InclusionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InclusionService {

    @Autowired
    private InclusionRepository inclusionRepository;

    public List<Inclusion> getAllInclusions() {
        return inclusionRepository.findAll();
    }

    public Inclusion saveInclusion(Inclusion inclusion) {
        return inclusionRepository.save(inclusion);
    }

    public Inclusion getInclusionById(Long id) {
        return inclusionRepository.findById(id).orElse(null);
    }

    public void deleteInclusion(Long id) {
        inclusionRepository.deleteById(id);
    }
}
