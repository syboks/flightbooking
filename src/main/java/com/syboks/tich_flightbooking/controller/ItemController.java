package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.entities.Item;
import com.syboks.tich_flightbooking.entities.Product;
import com.syboks.tich_flightbooking.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String showItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @PostMapping("/items/add")
    public String addItem(@ModelAttribute Product product) {
        itemService.save(product);
        return "redirect:/items";
    }
}
