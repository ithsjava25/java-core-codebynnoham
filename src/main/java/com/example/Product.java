package com.example;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public sealed abstract class Product permits FoodProduct, ElectronicsProduct {
    public static final Locale SWEDISH = new Locale.Builder()
                                                    .setLanguage("sv")
                                                    .setRegion("SE")
                                                    .build();

    public static final NumberFormat SWEDISH_NUMBER_FORMAT =
           NumberFormat.getInstance(SWEDISH);
    public static final NumberFormat  SWEDISH_CURRENCY_FORMAT =
            NumberFormat.getCurrencyInstance(SWEDISH);
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN, SWEDISH);

    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;

    protected Product(UUID id, String name, Category category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = validateField(price, "Price");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = validateField(price, "Price");
    }

    protected <T extends Number> T validateField(T field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException(fieldName + " cannot be null");
        BigDecimal bigDecimal = new BigDecimal(field.toString());
        if (bigDecimal.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        return field;
    }

    @Override
    public String toString() {
        return String.format("""
               Product ID   : %s
               Name         : %s
               Category     : %s
               Price        : %s
               """,
                getId(),
                getName(),
                getCategory(),
                SWEDISH_CURRENCY_FORMAT.format(getPrice())
                );
    }

    public abstract String productDetails();
}
