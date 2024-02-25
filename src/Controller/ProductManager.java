package Controller;

import ProductModel.Product;
import Utils.PaginatedList;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProductManager {
    private static final String FILE_NAME = "products.dat";
    private static List<Product> products = new ArrayList<>();
    private static long time;
    static {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            long start = System.currentTimeMillis();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                products.add(new Product(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
            long end = System.currentTimeMillis();
            time = (end - start)/1000;
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
        // Assuming 'products' is a List<Product> containing all your products
        PaginatedList<Product> paginatedProducts = new PaginatedList<>(products,   1); // Show   5 products per page

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Page " + (((PaginatedList<Product>) paginatedProducts).currentPage +   1) + " of " + paginatedProducts.numberOfPages());
            List<Product> currentPageProducts = paginatedProducts.getPage(paginatedProducts.currentPage +   1);
            for (Product product : currentPageProducts) {
                System.out.println(product); // Print product details
            }
            System.out.println("Enter   1 for next page,   2 for previous page,   3 for first page,   4 for last page,   5 to go to a specific page,   6 to exit:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case   1: // Next page
                    paginatedProducts.nextPage();
                    break;
                case   2: // Previous page
                    paginatedProducts.previousPage();
                    break;
                case   3: // First page
                    paginatedProducts.currentPage =   0;
                    break;
                case   4: // Last page
                    paginatedProducts.currentPage = paginatedProducts.numberOfPages() -  1;
                    break;
                case   5: // Go to a specific page
                    System.out.print("Enter the page number: ");
                    int pageNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    if (pageNumber >=  1 && pageNumber <= paginatedProducts.numberOfPages()) {
                        ((PaginatedList<Product>) paginatedProducts).currentPage = pageNumber -  1;
                    } else {
                        System.out.println("Invalid page number.");
                    }
                    break;
                case   6: // Exit
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
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

