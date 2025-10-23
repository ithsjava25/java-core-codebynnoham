package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class MainWarehouse {
    static void main(String[] args) {
        //create 3 warehouses
        Warehouse stockholm = Warehouse.getInstance("Stockholm");
        Warehouse gothenburg = Warehouse.getInstance("Gothenburg");
        Warehouse malmo = Warehouse.getInstance("Malmo");

        //create some Categories
        Category dairy = Category.of("Dairy");
        Category bakery = Category.of("Bakery");
        Category beverage = Category.of("Beverage");
        Category fruit = Category.of("Fruit");
        Category vegetable = Category.of("Vegetable");
        Category meat = Category.of("Meat");
        Category laptop = Category.of("Laptop");
        Category phone = Category.of("Phone");
        Category tablet = Category.of("Tablet");
        Category desktop = Category.of("Desktop");
        Category printer = Category.of("Printer");
        Category webcam = Category.of("Webcam");

        //create some Products
        Product beverage1 = new FoodProduct(UUID.randomUUID(), "Bonnet", beverage, new BigDecimal("10.99"), LocalDate.now().plusDays(10), new BigDecimal("0.25"));
        Product apple = new FoodProduct(UUID.randomUUID(), "Apple", fruit, new BigDecimal("2.99"), LocalDate.now().plusDays(10), new BigDecimal("0.25"));
        Product banana = new FoodProduct(UUID.randomUUID(), "Banana", fruit, new BigDecimal("1.49"), LocalDate.now().plusDays(7), new BigDecimal("0.30"));
        Product bread = new FoodProduct(UUID.randomUUID(), "Bread", bakery, new BigDecimal("22.99"), LocalDate.now().plusDays(3), new BigDecimal("0.50"));
        Product milk = new FoodProduct(UUID.randomUUID(), "Milk", dairy, new BigDecimal("12.50"), LocalDate.now().plusDays(5), new BigDecimal("1.00"));
        Product cheese = new FoodProduct(UUID.randomUUID(), "Cheese", dairy, new BigDecimal("44.89"), LocalDate.now().plusDays(20), new BigDecimal("0.75"));
        Product carrot = new FoodProduct(UUID.randomUUID(), "Carrot", vegetable, new BigDecimal("0.99"), LocalDate.now().plusDays(15), new BigDecimal("0.20"));
        Product chicken = new FoodProduct(UUID.randomUUID(), "Chicken", meat, new BigDecimal("25.59"), LocalDate.now().plusDays(4), new BigDecimal("2.00"));
        Product laptop1 = new ElectronicsProduct(UUID.randomUUID(), "Laptop1", laptop, new BigDecimal("1249.99"), 24, new BigDecimal("2.5"));
        Product phone1 = new ElectronicsProduct(UUID.randomUUID(), "Phone1", phone, new BigDecimal("7599.99"), 12, new BigDecimal("0.5"));
        Product tablet1 = new ElectronicsProduct(UUID.randomUUID(), "Tablet1", tablet, new BigDecimal("13499.99"), 18, new BigDecimal("0.8"));
        Product desktop1 = new ElectronicsProduct(UUID.randomUUID(), "Desktop1", desktop, new BigDecimal("2499.99"), 36, new BigDecimal("8.0"));
        Product printer1 = new ElectronicsProduct(UUID.randomUUID(), "Printer1", printer, new BigDecimal("1999.99"), 12, new BigDecimal("6.0"));
        Product webcam1 = new ElectronicsProduct(UUID.randomUUID(), "Webcam1", webcam, new BigDecimal("3499.99"), 6, new BigDecimal("0.3"));
        Product tv1 = new ElectronicsProduct(UUID.randomUUID(), "TV1", Category.of("TELEVISION"), new BigDecimal("22499.99"), 24, new BigDecimal("10.0"));

        //stock the stockholm warehouse
        stockholm.addProduct(apple);
        stockholm.addProduct(banana);
        stockholm.addProduct(bread);
        stockholm.addProduct(phone1);
        stockholm.addProduct(tv1);
        stockholm.addProduct(printer1);

        //stock the gothenburg warehouse
        gothenburg.addProduct(milk);
        gothenburg.addProduct(cheese);
        gothenburg.addProduct(carrot);
        gothenburg.addProduct(laptop1);
        gothenburg.addProduct(webcam1);
        gothenburg.addProduct(tablet1);

        //stock the malmo warehouse
        malmo.addProduct(chicken);
        malmo.addProduct(banana); //same product as in stockholm warehouse
        malmo.addProduct(printer1); //same product as in stockholm warehouse
        malmo.addProduct(desktop1);
        malmo.addProduct(phone1); //same product as in stockholm warehouse
        malmo.addProduct(apple); //same product as in stockholm warehouse


        //print all products in each warehouse
        System.out.println(" ".repeat(15) + "=".repeat(20) + "Products in the Stockholm Warehouse" + "=".repeat(20));
        printProductsInWarehouse(stockholm);
        System.out.println();

        System.out.println(" ".repeat(15) +  "=".repeat(20) + "Products in the Gothenburg Warehouse" + "=".repeat(20));
        printProductsInWarehouse(gothenburg);
        System.out.println();

        System.out.println(" ".repeat(15) +  "=".repeat(20) + "Products in the Malmo Warehouse" + "=".repeat(20));
        printProductsInWarehouse(malmo);
        System.out.println();

        //search for a product by productId in each warehouse
        System.out.printf("\nSearching for product with ID: %s in the Stockholm Warehouse%n", apple.getId());
        stockholm.getProductById(apple.getId())
                .ifPresentOrElse(
                product -> System.out.println(product),
                () -> System.out.printf("Product matching the ID: %s not found", apple.getId())
            );
        System.out.println();

        System.out.printf("%nSearching for product with ID: %s in the Gothenburg Warehouse%n", milk.getId());
        gothenburg.getProductById(milk.getId())
                .ifPresentOrElse(
                product -> System.out.println(product),
                () -> System.out.printf("Product matching the ID: %s not found", milk.getId())
            );
        System.out.println();

        System.out.printf("%nSearching for product with ID: %s in the Malmo Warehouse%n", chicken.getId());
        malmo.getProductById(chicken.getId())
                .ifPresentOrElse(
                product -> System.out.println(product),
                () -> System.out.printf("Product matching the ID: %s not found", chicken.getId())
            );
        System.out.println();

        //update price of a product in each warehouse
        System.out.printf("\nUpdating the price of %s in the Stockholm Warehouse from %s to %s%n",
                apple.getName(),
                Product.SWEDISH_CURRENCY_FORMAT.format(apple.getPrice()),
                Product.SWEDISH_CURRENCY_FORMAT.format(0.35));
        stockholm.updateProductPrice(apple.getId(), new BigDecimal("0.35"));
        stockholm.getProductById(apple.getId())
                .ifPresentOrElse(
                product -> System.out.println(product),
                () -> System.out.printf("Product matching the ID: %s not found", apple.getId())
            );
        System.out.println();
        stockholm.getChangedProducts().forEach(e -> System.out.println(e));
        System.out.println();

        System.out.printf("\nUpdating price of %s in the Gothenburg Warehouse from %s to %s%n",
                milk.getName(),
                Product.SWEDISH_CURRENCY_FORMAT.format(milk.getPrice()),
                Product.SWEDISH_CURRENCY_FORMAT.format(1.10));
        gothenburg.updateProductPrice(milk.getId(), new BigDecimal("1.10"));
        gothenburg.getProductById(milk.getId())
                .ifPresentOrElse(
                product -> System.out.println(product),
                () -> System.out.printf("Product matching the ID: %s not found", milk.getId())
            );
        System.out.println();
        gothenburg.getChangedProducts().forEach(e -> System.out.println(e));
        System.out.println();

        System.out.printf("\nUpdating price of %s in the Malmo Warehouse from %s to %s%n",
                chicken.getName(),
                Product.SWEDISH_CURRENCY_FORMAT.format(chicken.getPrice()),
                Product.SWEDISH_CURRENCY_FORMAT.format(2.20));
        malmo.updateProductPrice(chicken.getId(), new BigDecimal("2.20"));
        malmo.getProductById(chicken.getId())
                .ifPresentOrElse(
                product -> System.out.println(product),
                () -> System.out.printf("Product matching the ID: %s not found", chicken.getId())
            );
        System.out.println();
        malmo.getChangedProducts().forEach(e -> System.out.println(e));
        System.out.println();

        //try to update price of a product that does not exist in the warehouse
        System.out.printf("\nTrying to update the price of product with ID: %s in the Stockholm Warehouse%n", milk.getId());
        try {
            stockholm.updateProductPrice(milk.getId(), new BigDecimal("1.20"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.err.println("Unable to update price of a product that does not exist in the warehouse.");
        }

        //try to update price of a product with negative price
        System.out.printf("\nTrying to update price of %s in the Gothenburg Warehouse from %s to %s%n",
                milk.getName(),
                Product.SWEDISH_CURRENCY_FORMAT.format(milk.getPrice()),
                Product.SWEDISH_CURRENCY_FORMAT.format(-1.20));

        try {
            gothenburg.updateProductPrice(milk.getId(), new BigDecimal("-1.20"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.err.println("Unable to update price of a product with negative price.");
        }

        //search for all expired products in each warehouse
        System.out.println("\nExpired products in Stockholm Warehouse:");
        stockholm.expiredProducts()
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\nExpired products in Gothenburg Warehouse:");
        gothenburg.expiredProducts()
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\nExpired products in Malmo Warehouse:");
        malmo.expiredProducts()
                .forEach(x -> System.out.println(x));
        System.out.println();

        //search for all shippable products in each warehouse
        System.out.println("\nShippable products in Stockholm Warehouse:");
        stockholm.shippableProducts()
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\nShippable products in Gothenburg Warehouse:");
        gothenburg.shippableProducts()
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\nShippable products in Malmo Warehouse:");
        malmo.shippableProducts()
                .forEach(x -> System.out.println(x));
        System.out.println();

        //remove a product from each warehouse
        System.out.printf("\nRemoving product %s from Stockholm Warehouse%n", bread.getName());
        System.out.println(" ".repeat(20) + " Stockholm inventory before Product removal:");
        printProductsInWarehouse(stockholm);
        System.out.println(" ".repeat(20) + " Stockholm inventory after Product removal:");
        stockholm.remove(bread.getId());
        printProductsInWarehouse(stockholm);
        System.out.println();

        System.out.printf("\nRemoving product %s from Gothenburg Warehouse%n", carrot.getName());
        System.out.println(" ".repeat(20) + " Gothenburg inventory before Product removal:");
        printProductsInWarehouse(gothenburg);
        System.out.println(" ".repeat(20) + " Gothenburg inventory after Product removal:");
        gothenburg.remove(carrot.getId());
        printProductsInWarehouse(gothenburg);
        System.out.println();

        System.out.printf("\nRemoving product %s from Malmo Warehouse%n", cheese.getName());
        System.out.println("=".repeat(20) + " Malmo inventory before Product removal: " + "=".repeat(20));
        printProductsInWarehouse(malmo);
        System.out.println("=".repeat(20) + " Malmo inventory after Product removal: " + "=".repeat(20));
        malmo.remove(cheese.getId());
        printProductsInWarehouse(malmo);
        System.out.println();

        System.out.println("=".repeat(20) + "All methods below not part of the original requirements" + "=".repeat(20));


        //print total inventory value in each warehouse
        BigDecimal stockholmTotal = totalInventoryValue(stockholm);
        System.out.printf("The total inventory value in the Stockholm Warehouse is : %s%n",
                Product.SWEDISH_CURRENCY_FORMAT.format(stockholmTotal));
        System.out.println();

        BigDecimal gothenburgTotal = totalInventoryValue(gothenburg);
        System.out.printf("The total inventory value in the Gothenburg Warehouse is : %s%n",
                Product.SWEDISH_CURRENCY_FORMAT.format(gothenburgTotal));
        System.out.println();

        BigDecimal malmoTotal = totalInventoryValue(malmo);
        System.out.printf("Total inventory value in the Malmo Warehouse is : %s%n",
                Product.SWEDISH_CURRENCY_FORMAT.format(malmoTotal));
        System.out.println();

        //print total inventory value across all warehouses
        BigDecimal totalValue = stockholmTotal.add(gothenburgTotal).add(malmoTotal);
        System.out.printf("\nTotal inventory value across all warehouses: %s%n",
                Product.SWEDISH_CURRENCY_FORMAT.format(totalValue));
        System.out.println();

        //print all perishable products in each warehouse
        System.out.println("\ncom.example.Perishable products in Stockholm Warehouse:");
        stockholm.getProducts().stream()
                .filter(x -> x instanceof Perishable)
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\ncom.example.Perishable products in Gothenburg Warehouse:");
        gothenburg.getProducts().stream()
                .filter(x -> x instanceof Perishable)
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\ncom.example.Perishable products in Malmo Warehouse:");
        malmo.getProducts().stream()
                .filter(x -> x instanceof Perishable)
                .forEach(x -> System.out.println(x));
        System.out.println();

        //print all electronics products in each warehouse
        System.out.println("\nElectronics products in Stockholm Warehouse:");
        stockholm.getProducts().stream()
                .filter(x -> x instanceof ElectronicsProduct)
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\nElectronics products in Gothenburg Warehouse:");
        gothenburg.getProducts().stream()
                .filter(x -> x instanceof ElectronicsProduct)
                .forEach(x -> System.out.println(x));
        System.out.println();

        System.out.println("\nElectronics products in Malmo Warehouse:");
        malmo.getProducts().stream()
                .filter(x -> x instanceof ElectronicsProduct)
                .forEach(x -> System.out.println(x));
        System.out.println();

        printInventoryValueInWarehouses(stockholm, gothenburg, malmo);
    }

    /*  public static Map<com.example.Warehouse, ArrayList<com.example.Product>> populateWarehouse() {
        //create 3 warehouses
        com.example.Warehouse stockholm = com.example.Warehouse.getInstance("Stockholm");
        com.example.Warehouse gothenburg = com.example.Warehouse.getInstance("Gothenburg");
        com.example.Warehouse malmo = com.example.Warehouse.getInstance("Malmo");

        //create some Categories
        com.example.Category dairy = com.example.Category.of("Dairy");
        com.example.Category bakery = com.example.Category.of("Bakery");
        com.example.Category beverage = com.example.Category.of("Beverage");
        com.example.Category fruit = com.example.Category.of("Fruit");
        com.example.Category vegetable = com.example.Category.of("Vegetable");
        com.example.Category meat = com.example.Category.of("Meat");
        com.example.Category laptop = com.example.Category.of("Laptop");
        com.example.Category phone = com.example.Category.of("Phone");
        com.example.Category tablet = com.example.Category.of("Tablet");
        com.example.Category desktop = com.example.Category.of("Desktop");
        com.example.Category printer = com.example.Category.of("Printer");
        com.example.Category webcam = com.example.Category.of("Webcam");
        com.example.Category tv = com.example.Category.of("TELEVISION");


        Map<com.example.Warehouse, ArrayList<com.example.Product>> warehouseProductMap = new HashMap<>();

        warehouseProductMap.put(stockholm, new ArrayList<com.example.Product>());
        warehouseProductMap.put(gothenburg, new ArrayList<com.example.Product>());
        warehouseProductMap.put(malmo, new ArrayList<com.example.Product>());

        warehouseProductMap.get(stockholm).add(new com.example.FoodProduct(UUID.randomUUID(), "Apple", fruit, LocalDate.now().plusDays(10), new BigDecimal("0.25")));
        warehouseProductMap.get(stockholm).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Phone1", phone, 12, new BigDecimal("0.5")));
        warehouseProductMap.get(stockholm).add(new com.example.FoodProduct(UUID.randomUUID(), "Bread", bakery, LocalDate.now().plusDays(3), new BigDecimal("0.50")));
        warehouseProductMap.get(stockholm).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Sony TV", tv, 24, new BigDecimal("10.0")));
        warehouseProductMap.get(stockholm).add(new com.example.FoodProduct(UUID.randomUUID(), "Banana", fruit, LocalDate.now().plusDays(7), new BigDecimal("0.30")));
        warehouseProductMap.get(stockholm).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Printer1", printer, 12, new BigDecimal("6.0")));

        warehouseProductMap.get(gothenburg).add(new com.example.FoodProduct(UUID.randomUUID(), "Milk", dairy, LocalDate.now().plusDays(5), new BigDecimal("1.00")));
        warehouseProductMap.get(gothenburg).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Laptop1", laptop, 24, new BigDecimal("2.5")));
        warehouseProductMap.get(gothenburg).add(new com.example.FoodProduct(UUID.randomUUID(), "Carrot", vegetable, LocalDate.now().plusDays(15), new BigDecimal("0.20")));
        warehouseProductMap.get(gothenburg).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Webcam1", webcam, 6, new BigDecimal("0.3")));
        warehouseProductMap.get(gothenburg).add(new com.example.FoodProduct(UUID.randomUUID(), "Bonvita", beverage, LocalDate.now().plusDays(10), new BigDecimal("0.25")));
        warehouseProductMap.get(gothenburg).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Tablet1", tablet, 18, new BigDecimal("0.8")));


        warehouseProductMap.get(malmo).add(new com.example.FoodProduct(UUID.randomUUID(), "Cheese", dairy, LocalDate.now().plusDays(20), new BigDecimal("0.75")));
        warehouseProductMap.get(malmo).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Desktop1", desktop, 36, new BigDecimal("8.0")));
        warehouseProductMap.get(malmo).add(new com.example.FoodProduct(UUID.randomUUID(), "Chicken", meat, LocalDate.now().plusDays(4), new BigDecimal("2.00")));
        warehouseProductMap.get(malmo).add(new com.example.FoodProduct(UUID.randomUUID(), "Banana", fruit, LocalDate.now().plusDays(7), new BigDecimal("0.30")));
        warehouseProductMap.get(malmo).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Printer1", printer, 12, new BigDecimal("6.0")));
        warehouseProductMap.get(malmo).add(new com.example.ElectronicsProduct(UUID.randomUUID(), "Phone1", phone, 12, new BigDecimal("0.5")));

        return warehouseProductMap;
    }  */

    public static BigDecimal totalInventoryValue(Warehouse warehouse) {
        if (warehouse == null)
            throw new IllegalArgumentException("Warehouse cannot be null");
        return warehouse.getProducts().stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public static void printAllWarehouses(Map<Warehouse, ArrayList<Product>> warehouseProductMap) {
        if (warehouseProductMap == null || warehouseProductMap.isEmpty())
            throw new IllegalArgumentException("Warehouse product map cannot be null or empty");
        warehouseProductMap.forEach((key, value) -> {
            System.out.printf("\nProducts in the %s Warehouse:%n", key);
            value.forEach(product -> System.out.println(product));
            System.out.println();
        });
    }



    private static void printProductsInWarehouse(Warehouse ... warehouse) {
        if (warehouse == null)
            throw new IllegalArgumentException("Warehouse cannot be null");
        for (Warehouse w : warehouse) {
            //System.out.printf("=".repeat(20) + "Products in the %s Warehouse%n" + "=".repeat(20), w.getWarehouseName());
            w.getProducts()
                    .forEach(x -> System.out.println(x));
            System.out.println();
        }
    }

    public static void printInventoryValueInWarehouses(Warehouse ... warehouse) {
        if (warehouse == null)
            throw new IllegalArgumentException("Warehouse cannot be null");
        System.out.println("\n" + "=".repeat(20) + "Warehouse Summary" + "=".repeat(20));
        BigDecimal total = BigDecimal.ZERO;
        for (Warehouse w : warehouse) {
            BigDecimal subtotal = totalInventoryValue(w);
            System.out.printf("The total inventory value in the %s Warehouse is : %s",
                    w.getWarehouseName(),
                    Product.SWEDISH_CURRENCY_FORMAT.format(subtotal));
            total = total.add(subtotal);
            System.out.println();
        }
        System.out.printf("Total inventory value across all warehouses is : %s%n",
                Product.SWEDISH_CURRENCY_FORMAT.format(total));
    }
}
