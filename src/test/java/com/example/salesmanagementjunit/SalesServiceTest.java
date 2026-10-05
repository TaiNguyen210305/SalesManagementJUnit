package com.example.salesmanagementjunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class SalesServiceTest {
    private SalesService service;

    @BeforeEach
    void setUp() {
        service = new SalesService();
    }

    @Test
    void calculateSubtotalMultipliesPriceByQuantity() {
        assertEquals(300.0, service.calculateSubtotal(new Product("P01", "Keyboard", 150.0, 2)), 0.0001);
    }

    @Test
    void calculateSubtotalWorksForFractionalPrice() {
        assertEquals(1499.85, service.calculateSubtotal(new Product("P02", "Monitor", 499.95, 3)), 0.0001);
    }

    @Test
    void calculateSubtotalRejectsNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateSubtotal(null));
    }

    @ParameterizedTest
    @CsvSource({
        "999.99, 0.00",
        "1000.00, 50.00",
        "4999.99, 249.9995",
        "5000.00, 500.00",
        "9999.99, 999.999",
        "10000.00, 1500.00"
    })
    void calculateDiscountUsesAllRequiredBoundaries(double subtotal, double expectedDiscount) {
        assertEquals(expectedDiscount, service.calculateDiscount(subtotal), 0.0001);
    }

    @Test
    void calculateDiscountRejectsNegativeSubtotal() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateDiscount(-0.01));
    }

    @Test
    void calculateShippingChargesSmallOrders() {
        assertEquals(50.0, service.calculateShippingFee(1999.99), 0.0001);
    }

    @Test
    void calculateShippingIsFreeAtTwoThousand() {
        assertEquals(0.0, service.calculateShippingFee(2000.0), 0.0001);
    }

    @Test
    void calculateShippingIsFreeAboveTwoThousand() {
        assertEquals(0.0, service.calculateShippingFee(2000.01), 0.0001);
    }

    @Test
    void calculateTotalSubtractsDiscountAndAddsShipping() {
        assertEquals(1000.0, service.calculateTotal(new Product("P05", "Book", 1000.0, 1)), 0.0001);
    }

    @Test
    void calculateTotalForVipOrder() {
        assertEquals(8500.0, service.calculateTotal(new Product("P06", "Laptop", 10000.0, 1)), 0.0001);
    }

    @ParameterizedTest
    @CsvSource({
        "999.99, REGULAR",
        "1000.00, SILVER",
        "4999.99, SILVER",
        "5000.00, GOLD",
        "9999.99, GOLD",
        "10000.00, VIP"
    })
    void classifyCustomerUsesAllBoundaries(double total, String expectedType) {
        assertEquals(expectedType, service.classifyCustomer(total));
    }

    @Test
    void classifyCustomerRejectsNegativeTotal() {
        assertThrows(IllegalArgumentException.class, () -> service.classifyCustomer(-1.0));
    }

    @Test
    void productRejectsBlankId() {
        assertThrows(IllegalArgumentException.class, () -> new Product(" ", "Book", 10.0, 1));
    }

    @Test
    void productRejectsNonPositivePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("P03", "Book", 0.0, 1));
    }

    @Test
    void productRejectsNonPositiveQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Product("P04", "Book", 10.0, 0));
    }

    @ParameterizedTest
    @MethodSource("invalidProductInputs")
    void productRejectsInvalidRequiredFields(String id, String name, double price, int quantity) {
        assertThrows(IllegalArgumentException.class, () -> new Product(id, name, price, quantity));
    }

    private static Stream<Arguments> invalidProductInputs() {
        return Stream.of(
                Arguments.of("P05", "", 10.0, 1),
                Arguments.of("P06", "Book", -1.0, 1),
                Arguments.of("P07", "Book", 10.0, -1));
    }
}
