package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    List<Product> products = new ArrayList<>();

    public ProductController() {
        products.add(new Product(1, "Laptop", 75000));
        products.add(new Product(2, "Mobile", 25000));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return products;
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public String addProduct(@RequestBody Product product) {
        products.add(product);
        return "Product Added";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody Product updated) {
        for (Product p : products) {
            if (p.getId() == id) {
                products.remove(p);
                products.add(updated);
                return "Updated";
            }
        }
        return "Not Found";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        products.removeIf(p -> p.getId() == id);
        return "Deleted";
    }
}
