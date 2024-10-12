package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.entities.Inclusion;
import com.syboks.tich_flightbooking.services.InclusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inclusions")
public class InclusionController {

    @Autowired
    private InclusionService inclusionService;

    @GetMapping
    public String listInclusions(Model model) {
        List<Inclusion> inclusions = inclusionService.getAllInclusions();
        model.addAttribute("inclusions", inclusions);
        return "inclusion-list";  // Returns a Thymeleaf template named "inclusion-list.html"
    }

    @GetMapping("/new")
    public String showInclusionForm(Model model) {
        model.addAttribute("inclusion", new Inclusion());
        return "inclusion-form";  // Returns a Thymeleaf template named "inclusion-form.html"
    }

    @PostMapping
    public String saveInclusion(@ModelAttribute("inclusion") Inclusion inclusion) {
        inclusionService.saveInclusion(inclusion);
        return "redirect:/inclusions";  // Redirects to the list of inclusions
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Inclusion inclusion = inclusionService.getInclusionById(id);
        model.addAttribute("inclusion", inclusion);
        return "inclusion-form";  // Reuses the form template for editing
    }

    @GetMapping("/delete/{id}")
    public String deleteInclusion(@PathVariable("id") Long id) {
        inclusionService.deleteInclusion(id);
        return "redirect:/inclusions";
    }
}