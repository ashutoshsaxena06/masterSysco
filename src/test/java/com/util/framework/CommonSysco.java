package com.util.framework;

import com.util.framework.online.TestSyscoExecutor;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class CommonSysco {

	public static WebDriverWait wait;
	public static WebDriver driver;
	private final static Logger logger = Logger.getLogger(CommonSysco.class);

	/*
	public Boolean startSysco(WebDriver driver, String listName, String userID, String pwd)
			throws InterruptedException {

		try {
			driver.get("https://www.esysco.net/EOP/Login");
			// Wait For Page To Load
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			wait = new WebDriverWait(driver, 30);

			// pass login credentials
			try {
				loginSysco(driver, userID, pwd);
			} catch (WebDriverException we) {
				// driver.findElement(By.xpath("//input[@name='Start
				// Over']")).click();
				// ifForceLoginRequired(driver);
				we.printStackTrace();
			}

			Thread.sleep(2000);
			if (RandomAction.isFramePresent(driver) == true) {
				driver.switchTo().frame("botFrame");
				logger.info("switched to botFrame");
			}

			// Click on Sysco Market Express
			WebElement btn_SyscoMarketExpress = wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//input[@id='syscoMarketExpress']"))));

			Assert.assertEquals(btn_SyscoMarketExpress.isDisplayed(), true);

			btn_SyscoMarketExpress.click();
			logger.info("Sysco market express clicked");

			Thread.sleep(2000);

			// click List link
			WebElement lnk_List = wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//li/a[contains(@id,'listTab')]"))));
			lnk_List.click();
			logger.info("Clicked On List option");

			Thread.sleep(2000);

			// Select list
			WebElement lnk_OGname = wait.until(ExpectedConditions.elementToBeClickable(driver
					.findElement(By.xpath("//*[@id='" + listName + "']/..//[contains(text(),'" + listName + "')]"))));
			lnk_OGname.click();


			Thread.sleep(2000);

			Select ddl_MoreTools = new Select(
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("moreListToolsTop")))));
			ddl_MoreTools.selectByValue("exportList");
			logger.info("Selected export list Option from More Tools drop down");

			Thread.sleep(2000);
			logger.info("Export window displayed :-" + wait
					.until(ExpectedConditions
							.visibilityOf(driver.findElement(By.xpath("//div[@id='dialog-for-export']"))))
					.isDisplayed());

			Select ddl_expFormat = new Select(
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("expFormat")))));
			ddl_expFormat.selectByValue("6");
			logger.info("selected value for file type - 6");

			WebElement chk_IncludePricing = driver.findElement(By.xpath("//input[@id='expIncludePricingCheckBox']"));
			if (!chk_IncludePricing.isSelected()) {
				chk_IncludePricing.click();
				logger.info("checked to Include Pricing");
			} else {
				logger.info("Include Pricing already selected");
			}

			WebElement chk_IncludeStatus = driver.findElement(By.xpath("//input[@id='expIncludePSMSCheckBox']"));
			chk_IncludeStatus.click();
			logger.info("checked to Include Status - " + chk_IncludeStatus.isSelected());

			WebElement btn_Export = wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//div/button[contains(.,'Export')]"))));
			Assert.assertEquals(btn_Export.isDisplayed(), true);
			btn_Export.click();
			logger.info("clicked on Export");

			Thread.sleep(80000);

			WebElement lnk_Close = wait.until(
					ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@id='close']"))));
			lnk_Close.click();

			Thread.sleep(2000);

			WebElement inp_Close = wait.until(
					ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div/input[@id='close']"))));

			inp_Close.click();

			logger.info("Application closed");
			return true;

		} catch (WebDriverException we) {
			we.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		/*
		 * finally {
		 * 
		 * }

	}
	
	*/

	public static boolean loginSysco(WebDriver driver, String userName, String password) {
		try {driver.get("https://www.esysco.net/EOP/Login");
			wait = new WebDriverWait(driver, 30);
			// Enter username
		WebElement inp_user = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'USERID')]"))));
		inp_user.sendKeys(userName);

		// Enter Password
		WebElement inp_pwd = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'currentPassword')]"))));
		inp_pwd.sendKeys(password);

		// Click Login
		WebElement img_Login = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//img[@id='img_login']"))));
		img_Login.click();
		logger.info("Login Success");
		return  true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static void verifyLogin(WebDriver driver) {
		if (wait.until(
				ExpectedConditions.visibilityOf(driver.findElement((By.xpath("//td[@src='images/logo_sysco.png']")))))
				.isDisplayed()) {

			logger.info("Login Success");
		}
	}

	public static boolean ifForceLoginRequired(WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			if (driver.findElement(By.xpath("//td/*/*[contains(.,'Already Logged In')]")).isDisplayed()) {
				logger.info("User already logged in !!!");
				// driver.findElement(By.xpath("//input[contains(@type,'Password')]")).sendKeys("bocalago2016");
				// driver.findElement(By.xpath("//input[@value='Force
				// Login']")).click();
				// logger.info("forced login");
			}
			return true;
		} catch (WebDriverException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean startSysco(WebDriver driver, String AccountID, String listName, String userID, String pwd)
			throws InterruptedException {
		// Wait For Page To Load
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
		// pass login
		Thread.sleep(5000);

		if (RandomAction.isFramePresent(driver) == true) {
			driver.switchTo().frame("botFrame");
			logger.info("switched to botFrame");
		}

		// Click on Sysco Market Express
		WebElement btn_SyscoMarketExpress = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//input[@id='syscoMarketExpress']"))));

		Assert.assertEquals(btn_SyscoMarketExpress.isDisplayed(), true);

		btn_SyscoMarketExpress.click();
		logger.info("Sysco market express clicked");

		Thread.sleep(2000);

		if (AccountID != null) {
			if (wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath(".//*[@id='customer-select-popup']"))))
					.isDisplayed()) {
				selectAccount(AccountID);
			}
		} else {
			logger.info("No Account id is provided to select - " + AccountID);
		}

		// click List link
		WebElement lnk_List = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//li/a[contains(@id,'itemHistoryTab')]"))));
		lnk_List.click();
		logger.info("Clicked On Order Guide option");

		Thread.sleep(2000);

		// Select list
//			WebElement lnk_OGname = wait.until(ExpectedConditions.elementToBeClickable(driver
//					.findElement(By.xpath("//*[@id='" + listName + "']/*/*[contains(text(),'" + listName + "')]"))));
//			lnk_OGname.click();
		///  Calendar
		WebElement calendarDDL = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//select[contains(@id,'orderGuideDateRange')]"))));
		Select select = new Select(calendarDDL);
		select.selectByValue("0");
		logger.info("Clicked On DDL for Custom option");

		WebElement totalItems = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//span[contains(@id,'msgTotal2')]"))));

		WebElement startDateElement = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//input[contains(@id,'historystartdate')]"))));
		startDateElement.sendKeys(TestSyscoExecutor.startDate);
		logger.info("send start date for Custom option");

		WebElement endDateElement = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//input[contains(@id,'historyenddate')]"))));
		endDateElement.sendKeys(TestSyscoExecutor.endDate);
		logger.info("send end date for Custom option");

		WebElement exportLink = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//input[contains(@id,'exportOrderGuideLink')]"))));
		exportLink.click();
		logger.info("Export link clicked");


		// Choose List
		// List<WebElement> OG_Lists
		// =driver.findElements(By.xpath("//table[@id='sysRecomGrid']/*/tr"));
		// logger.info("No. of Rows in My List :-" +
		// OG_Lists.size());
		//
		// Thread.sleep(2000);
		// // Select list
		// for (WebElement List_Elements : OG_Lists) {
		// // logger.info(List_Elements.getText());
		// String OG_Element = List_Elements.getAttribute("id");
		// logger.info(OG_Element);
		// if (OG_Element.equalsIgnoreCase(listName)) {
		// driver.findElement(By.xpath("//table[@id='sysRecomGrid']/*/*/td/a[contains(.,"+
		// listName +")]")).click();
		//
		// }
		//
		// }

		Thread.sleep(5000);

//		Select ddl_MoreTools = new Select(
//				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("moreListToolsTop")))));
//		ddl_MoreTools.selectByValue("exportList");
//		logger.info("Selected export list Option from More Tools drop down");

		Thread.sleep(2000);
		logger.info("Export window displayed :-" + wait
				.until(ExpectedConditions
						.visibilityOf(driver.findElement(By.xpath("//div[@id='dialog-for-export']"))))
				.isDisplayed());

		Select ddl_expFormat = new Select(
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("expFormat")))));
		ddl_expFormat.selectByValue("6");
		logger.info("selected value for file type - 6");

		Thread.sleep(2000);
		WebElement chk_IncludePricing = driver.findElement(By.xpath("//input[@id='expIncludePricingCheckBox']"));
		chk_IncludePricing.click();
		if (!chk_IncludePricing.isSelected()) {
			chk_IncludePricing.click();
			logger.info("checked to Include Pricing");
		} else {
			logger.info("Include Pricing already selected");
		}
		Thread.sleep(2000);

		WebElement chk_IncludeStatus = driver.findElement(By.xpath("//input[@id='expIncludePSMSCheckBox']"));
		chk_IncludeStatus.click();
		logger.info("checked to Include Status - " + chk_IncludeStatus.isSelected());

		Thread.sleep(2000);

		// pre export check
		if (chk_IncludePricing.isSelected() && chk_IncludeStatus.isSelected()) {
			logger.info("pre export check status is Ok ");
			String id = "sysco" + AccountID + " " + RandomAction.getDate();
			RandomAction.errorScreenshot(driver, id);
		}

		WebElement btn_Export = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//div/button[contains(.,'Export')]"))));
		Assert.assertEquals(btn_Export.isDisplayed(), true);
		btn_Export.click();
		logger.info("clicked on Export");

		Thread.sleep(80000);

		WebElement lnk_Close = wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div/a[@id='clsLink']"))));
		lnk_Close.click();

		Thread.sleep(2000);

		WebElement inp_Close = wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div/input[@id='close']"))));

		inp_Close.click();
		logger.info("Application closed");
		return true;
	}


	private void selectAccount(String accountID) {
		WebElement lnk_AccountID = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//a[contains(text(),'" + accountID + "')]"))));
		lnk_AccountID.click();
		logger.info("Account location ID select - " + accountID);
	}

}
