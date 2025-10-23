package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public final class ElectronicsProduct extends Product implements Shippable {
    private final int warrantyMonths;
    private final BigDecimal weight; // in kilograms

    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super(id, name, category, price);
        this.warrantyMonths = validateField(warrantyMonths, "Warranty months");
        this.weight = validateField(weight, "Weight"); // in kilograms
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("""
                Warranty       : %d months
                Weight         : %s kg
                """,
                getWarrantyMonths(),
                SWEDISH_NUMBER_FORMAT.format(getWeight()));
    }

    @Override
    public String productDetails() {
        return String.format("Electronics: %s, Warranty: %s months", getName(), getWarrantyMonths());
    }

    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal basePrice = new BigDecimal("79.00");
        BigDecimal extraWeightCharge = new BigDecimal("49.00");
        BigDecimal weightLimit = new BigDecimal("5.00");
        return (this.weight.compareTo(weightLimit) > 0)
                ? basePrice.add(extraWeightCharge)
                : basePrice;
    }

    @Override
    public BigDecimal weight() {
        return this.weight;
    }
}
