package com.abderrahmane.elearning.authservice.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileSystem {
    public static void copyDirectoryContent (File source, File destination) {
        for (File childFile : source.listFiles()) {
            if (childFile.isDirectory()) {
                FileSystem.copyDirectory(childFile, destination);
            } else {
                FileSystem.copyFile(childFile, destination);
            }
        }
    }

    
    public static void copyDirectory (File source, File destination) {
        File dirname = new File(destination, source.getName());

        if (!dirname.exists()) dirname.mkdirs();

        copyDirectoryContent(source, dirname);
    }

    public static void copyFile(File source, File destination) {
        try (InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(new File(destination, source.getName()))) {
            in.transferTo(out);
            in.close();
            out.close();
        } catch (Exception ex) {
            System.out.print("[ERROR] " + ex.getMessage());
        }
    }
}
