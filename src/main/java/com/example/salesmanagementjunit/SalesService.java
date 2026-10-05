package com.example.salesmanagementjunit;

public class SalesService {

    public double calculateSubtotal(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        return product.getPrice() * product.getQuantity();
    }

    public double calculateDiscount(double subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }
        if (subtotal < 1000) {
            return 0;
        }
        if (subtotal < 5000) {
            return subtotal * 0.05;
        }
        if (subtotal < 10000) {
            return subtotal * 0.10;
        }
        return subtotal * 0.15;
    }

    public double calculateShippingFee(double subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }
        return subtotal < 2000 ? 50 : 0;
    }

    public double calculateTotal(Product product) {
        double subtotal = calculateSubtotal(product);
        double discount = calculateDiscount(subtotal);
        double shipping = calculateShippingFee(subtotal);
        return subtotal - discount + shipping;
    }

    public String classifyCustomer(double total) {
        if (total < 0) {
            throw new IllegalArgumentException("Total cannot be negative");
        }
        if (total < 1000) {
            return "REGULAR";
        }
        if (total < 5000) {
            return "SILVER";
        }
        if (total < 10000) {
            return "GOLD";
        }
        return "VIP";
    }
}
