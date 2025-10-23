package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class FoodProduct extends Product implements Perishable, Shippable {
    private final LocalDate expirationDate;
    private final BigDecimal weight; // in kilograms

    public FoodProduct(UUID id, String name, Category category, BigDecimal price , LocalDate expirationDate, BigDecimal weight) {
        super(id, name, category, price);
        this.expirationDate = expirationDate;
        this.weight = validateField(weight, "Weight"); // in kilograms
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("""
                Expiration Date : %s
                Weight          : %s kg
                """,
                getExpirationDate().format(DATE_FORMATTER),
                SWEDISH_NUMBER_FORMAT.format(getWeight()));
    }

    @Override
    public String productDetails() {
        return String.format("Food: %s, Expires: %s", getName(), getExpirationDate().format(DATE_FORMATTER));
    }

    @Override
    public BigDecimal calculateShippingCost() {
        return this.weight.multiply(new BigDecimal("50.00"));
    }

    @Override
    public BigDecimal weight() {
        return this.weight;
    }

    @Override
    public LocalDate expirationDate() {
        return this.expirationDate;
    }
}
