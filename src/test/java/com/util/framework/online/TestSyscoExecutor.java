package com.util.framework.online;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.framework.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSyscoExecutor extends CommonSysco {

    static final int maxtry = 3;
    private final static Logger logger = Logger.getLogger(TestSyscoExecutor.class);
    public static int rowIndex;
    public static String pr̥ojectPath = System.getProperty("user.dir");
    public static String inputFile = System.getProperty("user.home") + "\\Desktop\\ExportEngineInput.xlsx";
    // projectPath + "\\config\\ExportEngineInput.xlsx";
    public static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    public static String reportFile = System.getProperty("user.home") + "\\Desktop\\Reports\\SyscoOG_report\\ExportSummary_Sysco_"
            + new Date().toString().replace(":", "").replace(" ", "") + ".xlsx";
    // for Edge -
    // "C:\\Users\\Edge\\Desktop\\Reports\\SyscoOG_report\\ExportSummary_Sysco_" +
    // PageAction.getDate().toString().replace(" ", "_");
    // + new Date().toString().replace(":", "").replace(" ", "") + ".xlsx";
    // projectPath+ "\\Output_Summary\\ExportSummary_Sysco_" + new
    // Date().toString().replace(":", "").replace(" ", "")+".xlsx";
    public static int acno;
    public static XSSFWorkbook exportworkbook;
    public static XSSFSheet inputsheet;
    public static int AcColStatus, AcColdetail;
    public static FileOutputStream out;
    public static int totalNoOfRows;
    public static String folderDate;
    public static String currList = "";
    public static String emailMessageExport = "";
    public static String path = System.getProperty("user.home") + "\\Downloads\\chromedriver_win32\\chromedriver.exe";
    public static String project = System.getProperty("sheetName");
    public static String startDate = "04/01/2020";
    public static String endDate = "05/01/2020";
    static int retry = 0;
    public static String extentReport = System.getProperty("user.dir") + File.separator + "extentsReport" + File.separator + "Report.html";
    public static ExtentReports er;
    public static ExtentTest et;


    @BeforeSuite
    public static void set() throws IOException {
        er = new ExtentReports(System.getProperty("user.dir") + File.separator + "extentsReport/Report.html", true);
        er.addSystemInfo("Host Name", "Edge").addSystemInfo("Environment", "Windows Server")
                .addSystemInfo("User Name", "Ashutosh Saxena").addSystemInfo("Project", project);
        er.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extents-config.xml"));
        er.assignProject(project + " Online OG Export" );
        logger.info("received params from User - sheetName: " + System.getProperty("sheetName") + " and email: " + System.getProperty("email") );
    }

    @AfterSuite
    public static void sendMailAndclose() {
        try {
            er.flush();
            er.close();

            String emailMsg = "Daily " + project + " OG Export Status: " + RandomAction.getDate();

            SendMailSSL.sendReports(emailMsg, reportFile);
            logger.info("Email Sent with Attachment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @BeforeMethod
    public static void setUp() throws IOException {
        // to get the browser on which the UI test has to be performed.
        System.out.println("***********StartTest*********");
        RandomAction.deleteFiles("C:\\Users\\Edge\\Downloads");
        driver = RandomAction.openBrowser("Chrome", path);
        logger.info("Invoked browser .. ");
    }

    @AfterMethod
    public static void writeExcel() throws IOException {
        logger.info("Running Excel write method!");
        out = new FileOutputStream(new File(reportFile));
        exportworkbook.write(out);
        acno++;
        driver.quit();
    }

    @DataProvider(name = "testData")
    public static Object[][] testData() throws IOException {
        logger.info("Inside Dataprovider. Creating the Object Array to store test data inputs.");
        Object[][] td = null;
        try {
            // Get TestCase sheet data
            int totalNoOfCols = inputsheet.getRow(inputsheet.getFirstRowNum()).getPhysicalNumberOfCells();
            totalNoOfRows = inputsheet.getLastRowNum();
            logger.info(totalNoOfRows + " Accounts and Columns are: " + totalNoOfCols);
            td = new String[totalNoOfRows][totalNoOfCols];
            for (int i = 1; i <= totalNoOfRows; i++) {
                for (int j = 0; j < totalNoOfCols; j++) {
                    td[i - 1][j] = ExcelFunctions.getCellValue(inputsheet, i, j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Test Cases captured in the Object Array. Exiting dataprovider.");
        return td;
    }

    ////////////////////////////////////////////////
    @AfterClass
    public static void sendMail() {
        try {
            String emailMsg = "Monthly Reports " + project + " OG Export Status: " + RandomAction.getDate();
            SendMailSSL.sendReports(emailMsg, reportFile);
            logger.info("Email Sent with Attachment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void beforeData() throws Exception {
        // read excel data

        // get active accounts
        // launch browser

        // -- fail ->
        exportworkbook = ExcelFunctions.openFile(inputFile);
        logger.info("Test data read.");
        inputsheet = exportworkbook.getSheet(project);
        AcColStatus = ExcelFunctions.getColumnNumber("Export Status", inputsheet);
        AcColdetail = ExcelFunctions.getColumnNumber("Detailed Export Status", inputsheet);

        logger.info("Exiting before data.");
        // copy config file to report folder
        // ExcelFunctions.copySheet(exportworkbook, , );
    }

    @AfterTest
    public void closeResources() throws SQLException, IOException {
        logger.info("Closing the resources!");

        if (out != null) {
            logger.info("Closing file output stream object!");
            out.close();
        }
        if (driver != null) {
            logger.info("Closing the browser!");
            // TestCases.driver.close();
            driver.quit();
        }

        if (exportworkbook != null) {
            exportworkbook.close();
        }
    }

    @Test(dataProvider = "testData")
    public void Export_Mail_OG(String active, String accountID, String purveyor, String restaurant_name,
                               String username, String password, String listname, String accountNumber, String exportstatus,
                               String detailedstatus) {
        Boolean result = false;
        logger.info("Inside OG Export : Started exporting OG for different accounts");
        XSSFCell cell1, cell2;
        TestSyscoExecutor.rowIndex = Math.floorMod(TestSyscoExecutor.acno, TestSyscoExecutor.totalNoOfRows) + 1;

        System.out.println("Test Case test #" + TestSyscoExecutor.rowIndex);
        cell1 = TestSyscoExecutor.exportworkbook.getSheet(project).getRow(TestSyscoExecutor.rowIndex)
                .createCell(TestSyscoExecutor.AcColStatus);
        cell1.setCellValue("");
        cell2 = TestSyscoExecutor.exportworkbook.getSheet(project).getRow(TestSyscoExecutor.rowIndex)
                .createCell(TestSyscoExecutor.AcColdetail);
        cell2.setCellValue("");
        // if((cell1=TestSyscoExecutor.exportworkbook.getSheet(project).getRow(TestSyscoExecutor.rowIndex).getCell(TestSyscoExecutor.AcColStatus))==null){
        // cell1 =
        // TestSyscoExecutor.exportworkbook.getSheet(project).getRow(TestSyscoExecutor.rowIndex).createCell(TestSyscoExecutor.AcColStatus);
        // cell1.setCellValue("");
        // }
        // if((cell2=TestSyscoExecutor.exportworkbook.getSheet(project).getRow(TestSyscoExecutor.rowIndex).getCell(TestSyscoExecutor.AcColdetail))==null){
        // cell2 =
        // TestSyscoExecutor.exportworkbook.getSheet(project).getRow(TestSyscoExecutor.rowIndex).createCell(TestSyscoExecutor.AcColdetail);
        // cell2.setCellValue("");
        // }
        exportstatus = cell1.getStringCellValue();
        detailedstatus = cell2.getStringCellValue();
        et = er.startTest(restaurant_name);

        try {
            if (active.equalsIgnoreCase("Yes")) {
                // if list is not empty
                logger.info(restaurant_name + " for purveryor " + purveyor + " is Active !!");
                et.log(LogStatus.INFO, restaurant_name + " and purveryor " + purveyor + " and listname is" + listname);
                if (listname != null) {
                    String[] dates = listname.split("-");
                    startDate = dates[0];
                    endDate = dates[1];
                    if (loginSysco(driver, username.trim(), password.trim())) {
                        result = startSysco(driver, accountNumber, listname.trim(), username.trim(),
                                password.trim());
                    }else {
                        detailedstatus= "esysco.net website is down or issue with login";
                    }
                } else {
                    detailedstatus = "esysco.net website is down or issue with login";
                }
                    if (result.equals(true)) {
                        String targetPath = System.getProperty("user.home") + "\\Downloads\\SyscoMonthlyReports\\" + "20200601_" + restaurant_name.trim();
                        boolean dirCreated = new File(targetPath).mkdirs();
                        System.out.println("dir created for restaurant " + "Big Pink" + " - " + dirCreated);
                        File csvFile = RandomAction.getLatestFilefromDir(System.getProperty("user.home") + "\\Downloads\\", "csv");
                        FileUtils.copyFileToDirectory(csvFile, new File(targetPath));

                        emailMessageExport = "Pass";
                        exportstatus = "Pass";
                        detailedstatus = "OG exported succesfully";
                        et.log(LogStatus.PASS, detailedstatus);
                    } else {
                        emailMessageExport = "Failed";
                        exportstatus = "Failed";
                        detailedstatus = "OG export Failed";
                        et.log(LogStatus.FAIL, detailedstatus);
                    }

                Thread.sleep(5000);
            } else {
                logger.info(restaurant_name + " for purveryor " + purveyor + " is not Active !!");
                exportstatus = "Not Active";
                et.log(LogStatus.SKIP, exportstatus);
            }
            cell1.setCellValue(exportstatus);
            cell2.setCellValue(detailedstatus);

            logger.info("Exiting test method");

        } catch (Exception e) {
            e.printStackTrace();
            exportstatus = "Failed";
            detailedstatus = detailedstatus != null  ?  detailedstatus : "Some technical issue ocurred during export";
            cell1.setCellValue(exportstatus);
            cell2.setCellValue(detailedstatus);
            logger.info(detailedstatus + " for restaurant - " + restaurant_name);
            et.log(LogStatus.FAIL, exportstatus + " - "+ detailedstatus);
            Assert.assertTrue(false);
        }
        logger.info(emailMessageExport.trim());
    }
    ////////////////////////////////////////////////////

}