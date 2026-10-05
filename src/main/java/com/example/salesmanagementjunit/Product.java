/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.salesmanagementjunit;



public class Product {

    private String productId;
    private String productName;
    private double price;
    private int quantity;

    public Product(String productId, String productName,
                   double price, int quantity) {

      if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException(
                "Product ID is required");
        }

       if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException(
                "Product name is required");
        }

        if (price <= 0) {
            throw new IllegalArgumentException(
                "Price must be greater than 0");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than 0");
        }

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
