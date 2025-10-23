package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Category {
    private static final Map<String, Category> CATEGORY_CACHE_MAP = new HashMap<>();
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null)
            throw new IllegalArgumentException("Category name can't be null");
        if (name.isBlank())
            throw new IllegalArgumentException("Category name can't be blank");

        String trimmedName = name.trim().toLowerCase();
        String validatedName = trimmedName.substring(0, 1).toUpperCase().
                concat(trimmedName.substring(1));

        return CATEGORY_CACHE_MAP.computeIfAbsent(validatedName, key -> new Category(key));
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
