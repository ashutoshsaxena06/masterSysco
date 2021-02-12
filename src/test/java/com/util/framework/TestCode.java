package com.util.framework;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestCode {

    public static void main(String[] args) throws IOException {
        String restaurant_name = "Big Pink";
        String targetPath = System.getProperty("user.home") + "\\Downloads\\SyscoReports\\" + "20200601_" + restaurant_name.trim();
        boolean dirCreated = new File(targetPath).mkdirs();
        System.out.println("dir created for restaurant " + restaurant_name + " - " + dirCreated);
        String listname = "04/01/2020-05/01/2020";
        String fileName = listname.replaceAll("/", "") + "_" + restaurant_name.trim() + ".csv";
//        File csvFile= RandomAction.getLatestFilefromDir(System.getProperty("user.home") + "\\Downloads\\", "csv");
//        FileUtils.copyFileToDirectory(csvFile, new File(targetPath));
        boolean fileRenamed = RandomAction.getLatestFilefromDir(System.getProperty("user.home") + "\\Downloads\\", "csv").renameTo(new File(targetPath+File.separator+fileName));
        System.out.println("renamed file for restaurant " + restaurant_name + " - " + fileRenamed);
    }
}
