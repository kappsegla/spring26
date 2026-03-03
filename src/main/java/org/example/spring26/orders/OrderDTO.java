package org.example.spring26.orders;

public record OrderDTO(String orderId, String userId, String productName, int quantity) {
}
