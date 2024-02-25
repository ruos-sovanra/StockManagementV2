package Controller;

import ProductModel.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProductManager {
    private static final String FILE_NAME = "products.dat";
    private static List<Product> products = new ArrayList<>();

    static {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            long start = System.currentTimeMillis();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                products.add(new Product(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
            long end = System.currentTimeMillis();
            System.out.println("Time taken to read products: " + (end - start)/1000 + "s");
        } catch (FileNotFoundException ex) {
            // Handle the case where the file does not exist
            try {
                new File(FILE_NAME).createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void randomProduct() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of records you want to generate: ");
        int numberOfRecord = input.nextInt();
        Random random = new Random();
        for (int i =  0; i <numberOfRecord; i++) {
            String id = String.valueOf(random.nextInt(123456789) +  1); // Generate a random ID between  1 and  1,000,000
            String name = "Product" + id; // Simple name generation for demonstration
            double price = random.nextDouble() *  1000; // Generate a random price between  0 and  1000
            Product product = new Product(id, name, price);
            products.add(product);
        }
        long start = System.currentTimeMillis();
        writeProductsToFile();
        long end = System.currentTimeMillis();
        System.out.println("Time taken to generate and write products: " + (end - start)/1000 + "s");
    }
    public static void createProduct(Product product) {
        long start = System.currentTimeMillis();
        products.add(product);
        writeProductsToFile();
        long end = System.currentTimeMillis();
        System.out.println("Time taken to create product: " + (end - start) + "ms");
    }

    public static void updateProduct(String id, String name, double price) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setName(name);
                product.setPrice(price);
                break;
            }
        }
        writeProductsToFile();
    }

    public static void deleteProduct(String id) {
        products.removeIf(product -> product.getId().equals(id));
        writeProductsToFile();
    }

    public static Product readProduct(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
    public void showProducts() {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }
    private static void writeProductsToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Product product : products) {
                writer.write(product.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
