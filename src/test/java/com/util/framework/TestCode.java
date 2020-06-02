package com.util.framework;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TestCode {

    public static void main(String[] args) throws IOException {
        String targetPath = "C:\\Users\\ASHUTOSH\\Downloads\\Sysco\\20200601_Big Pink";
        File targetDir = new File(targetPath);
        boolean dirCreated = targetDir.mkdirs();
        System.out.println("dir created for restaurant " + "Big Pink" + " - " + dirCreated);
        File csvFile = RandomAction.getLatestFilefromDir(System.getProperty("user.home") + "\\Downloads\\", "csv");
        FileUtils.copyFileToDirectory(csvFile,new File(targetPath));
    }
}
