package com.util.framework.online;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.framework.CommonSysco;
import com.util.framework.ExcelFunctions;
import com.util.framework.RandomAction;
import com.util.framework.SendMailSSL;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestSyscoExecutor extends CommonSysco {

    static final int maxtry = 3;
    private final static Logger logger = Logger.getLogger(TestSyscoExecutor.class);
    public static int rowIndex;
    public static String pr̥ojectPath = System.getProperty("user.dir");
    public static String inputFile = System.getProperty("user.home") + "\\Desktop\\ExportEngineInput.xlsx";
    // projectPath + "\\config\\ExportEngineInput.xlsx";
    public static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    public static String reportFile = System.getProperty("user.home") + "\\Desktop\\Reports\\SyscoMonthly_report\\ExportSummary_Sysco_"
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
    //    public static FileOutputStream out;
    public static int totalNoOfRows;
    public static String folderDate;
    public static String currList = "";
    public static String emailMessageExport = "";
    public static String path = System.getProperty("user.home") + "\\Downloads\\chromedriver_win32\\chromedriver.exe";
    public static String project = "SyscoReports";
    public static LocalDate date;
    public static String extentReport = System.getProperty("user.dir") + File.separator + "extentsReport" + File.separator + "Report.html";
    public static ExtentReports er;
    public static ExtentTest et;
    public static FileOutputStream out;

    static int retry = 0;

    @BeforeSuite
    public static void set() throws IOException {
        er = new ExtentReports(System.getProperty("user.dir") + File.separator + "extentsReport/Report.html", true);
        er.addSystemInfo("Host Name", "Edge").addSystemInfo("Environment", "Windows Server")
                .addSystemInfo("User Name", "Ashutosh Saxena").addSystemInfo("Project", project);
        er.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extents-config.xml"));
        er.assignProject(project + " Online OG Export");
        logger.info("received params from User - sheetName: " + System.getProperty("sheetName") + " and email: " + System.getProperty("email"));
//
//        File file = new File(inputFile);
//        FileUtils.copyFile(file, new File(reportFile));
////        boolean fileRenamed = file.renameTo(new File(reportFile));
//        System.out.println("copied report file ");
    }

    @AfterSuite
    public static void sendMailAndclose() {
        try {
            er.flush();
            er.close();

            String emailMsg = "Monthly " + project + " OG Export Status: " + RandomAction.getDate();

//            SendMailSSL.sendReports(emailMsg, reportFile);
            logger.info("Email Sent with Attachment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public static void setUp() throws IOException {
        // to get the browser on which the UI test has to be performed.
        System.out.println("***********StartTest*********");
        RandomAction.deleteFiles(System.getProperty("user.home")+"\\Downloads");
        driver = RandomAction.openBrowser("Chrome", path);
        logger.info("Invoked browser .. ");
    }

    @AfterMethod
    public static void writeExcel() throws IOException {
        logger.info("Running Excel write method!");
        out = new FileOutputStream(new File(reportFile));
        exportworkbook.write(out);
        er.endTest(et);
        acno++;
        try {
            driver.quit();
        } catch (Exception e) {
            System.out.println("already closed");
        }
    }

    @DataProvider(name = "testData")
    public static Object[][] testData() throws IOException {
        logger.info("Inside Dataprovider. Creating the Object Array to store test data inputs.");
        Object[][] td = null;
        try {
            // Get TestCase sheet data
            int totalNoOfCols = inputsheet.getRow(inputsheet.getFirstRowNum()).getPhysicalNumberOfCells();
            totalNoOfRows = inputsheet.getPhysicalNumberOfRows();
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
        logger.info("Test Cases captured in the Object Array. Exiting data provider.");
        return td;
    }

    ////////////////////////////////////////////////

    @BeforeTest
    public void beforeData() throws Exception {
        exportworkbook = ExcelFunctions.openFile(inputFile);
        logger.info("Test data read.");
        inputsheet = exportworkbook.getSheet(project);
        AcColStatus = ExcelFunctions.getColumnNumber("Export Status", inputsheet);
        AcColdetail = AcColStatus + 1;
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
                               String username, String password, String exportDate, String accountNumber, String exportstatus,
                               String detailedstatus) {
        String result = null;
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

        exportstatus = cell1.getStringCellValue();
        detailedstatus = cell2.getStringCellValue();
        et = er.startTest(restaurant_name.trim() + "_" + exportDate.trim());

        try {
            if (active.equalsIgnoreCase("Yes")) {
                // if list is not empty
                logger.info(restaurant_name + " for purveryor " + purveyor + " is Active !!");
                et.log(LogStatus.INFO, restaurant_name + " and purveryor " + purveyor + " and exportDate is" + exportDate);

                if (loginSysco(driver, username.trim(), password.trim())) {
                    setDates(exportDate);
                    result = startSysco(driver, accountNumber);
                } else {
                    detailedstatus = "esysco.net website is down or issue with login";
                    result = "NA";
                }
                detailedstatus = result;
                if (result.equals("success")) {
                    String targetPath = System.getProperty("user.home") + "\\Downloads\\SyscoReports\\" + exportDate +"_" + restaurant_name.trim();
                    boolean dirCreated = new File(targetPath).mkdirs();
                    System.out.println("dir created for restaurant " + restaurant_name + " - " + dirCreated);
                    String fileName = exportDate + "_" + restaurant_name.trim() + ".csv";
//        File csvFile= RandomAction.getLatestFilefromDir(System.getProperty("user.home") + "\\Downloads\\", "csv");
//        FileUtils.copyFileToDirectory(csvFile, new File(targetPath));
                    boolean fileRenamed = RandomAction.getLatestFilefromDir(System.getProperty("user.home") + "\\Downloads\\", "csv").renameTo(new File(targetPath + File.separator + fileName));
                    System.out.println("renamed file for restaurant " + restaurant_name + " - " + fileRenamed);
                    emailMessageExport = "Pass";
                    exportstatus = "Pass";
                    et.log(LogStatus.PASS, detailedstatus);
                } else if (result.equals("No Purchase history available")) {
                    emailMessageExport = "Pass";
                    exportstatus = "Pass";
                    et.log(LogStatus.PASS, detailedstatus);
                } else {
                    emailMessageExport = "Failed";
                    exportstatus = "Failed";
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
            detailedstatus = "Some technical issue occurred during export";
            cell1.setCellValue(exportstatus);
            cell2.setCellValue(detailedstatus);
            logger.info(detailedstatus + " for restaurant - " + restaurant_name);
            et.log(LogStatus.FAIL, exportstatus + " - " + detailedstatus);
//            Assert.fail();
        }
        logger.info(emailMessageExport.trim());
    }

    private void setDates(String exportDate) {
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), getMonth(exportDate));
        date = yearMonth.atEndOfMonth();
    }

    private int getMonth(String exportDate) {
        switch (exportDate) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return LocalDate.now().getMonth().getValue();
        }
    }
    ////////////////////////////////////////////////////

}
