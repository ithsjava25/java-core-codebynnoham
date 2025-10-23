package com.example;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class Warehouse {
    private static final Logger log = (Logger) LoggerFactory.getLogger(Warehouse.class);
    private static final Map<String, Warehouse> WAREHOUSE_CACHE_MAP = new HashMap<>();
    private final String warehouseName;
    private final Map<UUID, Product> productMap;
    private final Set<Product> changedProductsSet;

    private Warehouse(String warehouseName) {
        this.warehouseName = warehouseName;
        this.productMap = new HashMap<>();
        this.changedProductsSet = new HashSet<>();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public Map<UUID, Product> getProductMap() {
        return productMap;
    }

    public Set<Product> getChangedProductsSet() {
        return changedProductsSet;
    }

    //Singleton method to create a new warehouse or return an existing warehouse
    public static Warehouse getInstance(String warehouseName) {
        if (warehouseName == null) {
            log.error("Warehouse name cannot be null");
            throw new IllegalArgumentException("Warehouse name cannot be null");
        }

        if (warehouseName.isBlank()) {
            log.error("Warehouse name cannot be blank");
            throw new IllegalArgumentException("Warehouse name cannot be blank");
        }

        String trimmedName = warehouseName.trim().toLowerCase();
        String validatedName = trimmedName.substring(0, 1).toUpperCase().
                concat(trimmedName.substring(1));
        log.info("Creating warehouse: {}", validatedName);
        return WAREHOUSE_CACHE_MAP.computeIfAbsent(validatedName, key -> new Warehouse(key));
    }

    public static Warehouse getInstance() {
        return getInstance("Default");
    }


    // Method to add a product to a Warehouse's productMap
    public void addProduct(Product product) {
        if (product == null) {
            log.error("Product cannot be null");
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (productMap.containsKey(product.getId())) {
            log.warn("Product with ID: {} already exists in warehouse: {}. Existing product will be overwritten.", product.getId(), warehouseName);
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        log.info("Successfully added product with ID: {} to warehouse:{}.", product.getId(), warehouseName);
        productMap.put(product.getId(), product);
    }
    //}

    // Method to return all product from a Warehouse's productMap
    public List<Product> getProducts() {
        List<Product> p = List.copyOf(productMap.values());
        if (p.isEmpty()) {
            log.warn("No product(s) found in warehouse: {}.", warehouseName);
            return List.of();
        } else {
            log.info("Successfully retrieved all product(s) from warehouse: {}.", warehouseName);
            return p;
        }
    }

    // Method to return a product by its ID from a Warehouse's productMap
    public Optional<Product> getProductById(UUID productId) {
        if (productId == null) {
            log.error("The product ID cannot be null");
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        log.info("Successfully retrieved product with ID: {} from warehouse: {}.", productId, warehouseName);
        return Optional.ofNullable(productMap.get(productId));
    }

    // Method to update the price of a product by its ID from a Warehouse's productMap
    public void updateProductPrice(UUID productId, BigDecimal newPrice) {
        if (productId == null) {
            log.error("The Product ID cannot be null");
            throw new IllegalArgumentException("The Product ID cannot be null");
        }
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("The price of the Product cannot be null, negative or zero");
            throw new IllegalArgumentException("The price of the Product cannot be null, negative or zero");
        }
        Product product = productMap.get(productId);
        if (product == null) {
            log.error("Product with ID: {} not found in {} warehouse.", productId, warehouseName);
            throw new NoSuchElementException("com.example.Product not found with id:");
            //throw new NoSuchElementException(String.format("Product not found with ID: %s.", productId));
        }
        product.setPrice(newPrice);
        changedProductsSet.add(product);
        log.info("Successfully updated price of product with ID: {} to {} in warehouse: {}.",
                productId,
                Product.SWEDISH_CURRENCY_FORMAT.format(product.getPrice()),
                warehouseName);
        log.info("The updated product details are: {}.", product);
    }

    public Set<Product> getChangedProducts() {
        log.info("Successfully retrieved all changed product(s) from warehouse: {}.", warehouseName);
        return Set.copyOf(changedProductsSet);
    }

    public List<Product> expiredProducts() {
        List<Product> expiredProducts = productMap.values().stream()
                        .filter(product -> product instanceof Perishable perishable && perishable.isExpired())
                        .toList();

        if (expiredProducts.isEmpty()) {
            log.warn("No expired product(s) found in warehouse: {}.", warehouseName);
            return List.of();
        }
            log.info("Successfully retrieved all expired product(s) from warehouse: {}.", warehouseName);
            return expiredProducts;
    }

    public List<Product> shippableProducts() {
        if (productMap.values().stream()
                .noneMatch(p -> p instanceof Shippable)) {
            log.warn("No shippable product(s) found in warehouse: {}.", warehouseName);
            return List.of();
        } else {
            log.info("Successfully retrieved all shippable product(s) from warehouse: {}.", warehouseName);
            return productMap.values().stream()
                        .filter(product -> product instanceof Shippable)
                        .toList();
        }
    }

    public void remove(UUID productId) {
        if (productId == null) {
            log.error("Product ID cannot be null");
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Product removedProduct = productMap.remove(productId);
        if (removedProduct != null) {
            changedProductsSet.remove(removedProduct);
            log.info("Successfully removed product with ID: {} from warehouse: {}.", productId, warehouseName);
        } else {
            log.warn("Product with ID: {} not found in warehouse: {}. No action taken.", productId, warehouseName);
        }
    }

    public void clearProducts() {
        productMap.clear();
        changedProductsSet.clear();
        log.info("Successfully cleared all products from warehouse: {}.", warehouseName);
    }

    public boolean isEmpty() {
        return productMap.isEmpty();
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return productMap.values().stream()
                .collect(Collectors.groupingBy(product -> product.getCategory()));
    }
}
