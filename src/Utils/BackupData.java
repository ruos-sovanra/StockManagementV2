package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupData {
    public static void backupProducts() {
        String backupDir = "backup";
        String fileName = "products.txt";
        String backupFileName = "database_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".bak";

        // Ensure backup directory exists
        File dir = new File(backupDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Create backup file path
        Path sourcePath = Paths.get(fileName);
        Path targetPath = Paths.get(backupDir, backupFileName);

        // Copy the file to the backup directory
        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup created: " + targetPath);
        } catch (IOException e) {
            System.err.println("Backup failed: " + e.getMessage());
        }
    }
}
