package com.syboks.tich_flightbooking.services;

import com.syboks.tich_flightbooking.entities.Pkg;
import com.syboks.tich_flightbooking.repository.PkgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PkgService {

    @Autowired
    private PkgRepository pkgRepository;

    public List<Pkg> getAllPackages() {
        return pkgRepository.findAll();
    }

    public Pkg savePackage(Pkg pkg) {
        return pkgRepository.save(pkg);
    }

    public Pkg getPackageById(Long id) {
        return pkgRepository.findById(id).orElse(null);
    }

    public void deletePackage(Long id) {
        pkgRepository.deleteById(id);
    }
}