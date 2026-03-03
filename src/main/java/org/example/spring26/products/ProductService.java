package org.example.spring26.products;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final List<Product> products = List.of(
            new Product("iPhone 15", "phones", 999.0),
            new Product("Samsung Galaxy S24", "phones", 899.0),
            new Product("Sony WH-1000XM5", "audio", 399.0),
            new Product("MacBook Air M3", "computers", 1299.0),
            new Product("Pixel 8", "phones", 699.0)
    );

    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return products;
        }
        return products.stream()
                .filter(p -> p.category().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
