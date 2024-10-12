package com.syboks.tich_flightbooking.services;

import com.syboks.tich_flightbooking.entities.Item;
import com.syboks.tich_flightbooking.entities.Product;
import com.syboks.tich_flightbooking.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Product> findAll() {
        return itemRepository.findAll();
    }

    public Product save(Product product) {
        return itemRepository.save(product);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Product findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }
}

