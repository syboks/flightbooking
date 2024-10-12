package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.entities.Inclusion;
import com.syboks.tich_flightbooking.entities.Pkg;
import com.syboks.tich_flightbooking.services.InclusionService;
import com.syboks.tich_flightbooking.services.PkgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/packages")
public class PkgController {
    @Autowired
    private PkgService pkgService;
    @Autowired
    private InclusionService inclusionService;

    @GetMapping
    public String listPackages(Model model) {
        List<Pkg> packages = pkgService.getAllPackages();
        model.addAttribute("packages", packages);
        return "package-list";  // Returns a Thymeleaf template named "package-list.html"
    }

    //@GetMapping("/packages/{id}")
    @GetMapping("/{id}")
    @ResponseBody
    public Pkg getPkg(@PathVariable Long id) {
        return pkgService.getPackageById(id);
    }

    @GetMapping("/new")
    public String showPackageForm(Model model) {
        List<Inclusion> inclusions = inclusionService.getAllInclusions();
        model.addAttribute("pkg", new Pkg());
        model.addAttribute("inclusions", inclusions);
        return "package-form";  // Returns a Thymeleaf template named "package-form.html.html"
    }

    @PostMapping
    public String savePackage(@ModelAttribute("pkg") Pkg pkg) {
        pkgService.savePackage(pkg);
        return "redirect:/packages";  // Redirects to the list of packages
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Pkg pkg = pkgService.getPackageById(id);
        List<Inclusion> inclusions = inclusionService.getAllInclusions();
        model.addAttribute("pkg", pkg);
        model.addAttribute("inclusions", inclusions);
        return "package-form";  // Reuses the form template for editing
    }

    @GetMapping("/delete/{id}")
    public String deletePackage(@PathVariable("id") Long id) {
        pkgService.deletePackage(id);
        return "redirect:/packages";
    }
}
