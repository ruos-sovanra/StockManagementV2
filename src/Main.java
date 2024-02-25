import Controller.ProductManager;
import ProductModel.Product;
import Utils.BackupData;
import Utils.RestoreData;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductManager productManager = new ProductManager();
        BackupData backupData = new BackupData();
        RestoreData restoreData = new RestoreData();
        boolean exit = false;


        while (!exit) {
            System.out.println("Select an option:");
            System.out.println("1. Create a product");
            System.out.println("2. Update a product");
            System.out.println("3. Delete a product");
            System.out.println("4. Search for a product");
            System.out.println("5. Show product");
            System.out.println("6. Backup products");
            System.out.println("7. Random product");
            System.out.println("8. Exit");
            System.out.print("Enter your choice (1-8): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case  1: // Create a product
                    System.out.print("Enter product ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    productManager.createProduct(new Product(id, name, price));
                    scanner.nextLine(); // Consume newline left-over
                    break;
                case  2: // Update a product
                    System.out.print("Enter ID of the product to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new product name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new product price: ");
                    double newPrice = scanner.nextDouble();
                    ProductManager.updateProduct(updateId, newName, newPrice);
                    scanner.nextLine(); // Consume newline left-over
                    break;
                case  3: // Delete a product
                    System.out.print("Enter ID of the product to delete: ");
                    String deleteId = scanner.nextLine();
                    ProductManager.deleteProduct(deleteId);
                    break;
                case  4: // Search for a product
                    System.out.print("Enter ID of the product to read: ");
                    String readId = scanner.nextLine();
                    Product product = ProductManager.readProduct(readId);
                    if (product != null) {
                        System.out.println("Found product: " + product);
                    } else {
                        System.out.println("ProductModel.Product not found.");
                    }
                    break;
                case  5: // Exit
                    productManager.showProducts();
                    break;
                case  6: // Exit
                    backupData.backupProducts();
                    break;
                case  7: // Exit
                    productManager.randomProduct();
                    break;
                case  8: // Exit
                    restoreData.restoreProducts();
                    break;
                case  9: // Exit
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
