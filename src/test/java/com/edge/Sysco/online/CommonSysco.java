package com.edge.Sysco.online;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.util.framework.RandomAction;

import junit.framework.Assert;

public class CommonSysco {

	public static WebDriverWait wait;
	public static WebDriver driver;

	public void startSysco(WebDriver driver, String listName, String userID, String pwd) throws InterruptedException {

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
			//	ifForceLoginRequired(driver);
				we.printStackTrace();
			}

			Thread.sleep(2000);
			if (RandomAction.isFramePresent(driver) == true) {
				driver.switchTo().frame("botFrame");
				System.out.println("switched to botFrame");
			}

			// Click on Sysco Market Express
			WebElement btn_SyscoMarketExpress = wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//input[@id='syscoMarketExpress']"))));

			Assert.assertEquals(btn_SyscoMarketExpress.isDisplayed(), true);

			btn_SyscoMarketExpress.click();
			System.out.println("Sysco market express clicked");

			Thread.sleep(2000);

			// click List link
			WebElement lnk_List = wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//li/a[contains(@id,'listTab')]"))));
			lnk_List.click();
			System.out.println("Clicked On List option");

			Thread.sleep(2000);
			
			// Select list
			WebElement lnk_OGname = wait.until(ExpectedConditions.elementToBeClickable(driver
					.findElement(By.xpath("//*[@id='" + listName + "']/*/*[contains(text(),'" + listName +"')]"))));
			lnk_OGname.click();

			// Choose List
			// List<WebElement> OG_Lists
			// =driver.findElements(By.xpath("//table[@id='sysRecomGrid']/*/tr"));
			// System.out.println("No. of Rows in My List :-" +
			// OG_Lists.size());
			//
			// Thread.sleep(2000);
			// // Select list
			// for (WebElement List_Elements : OG_Lists) {
			// // System.out.println(List_Elements.getText());
			// String OG_Element = List_Elements.getAttribute("id");
			// System.out.println(OG_Element);
			// if (OG_Element.equalsIgnoreCase(listName)) {
			// driver.findElement(By.xpath("//table[@id='sysRecomGrid']/*/*/td/a[contains(.,"+
			// listName +")]")).click();
			//
			// }
			//
			// }

			Thread.sleep(2000);

			Select ddl_MoreTools = new Select(
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("moreListToolsTop")))));
			ddl_MoreTools.selectByVisibleText("Export List");
			System.out.println("Selected export list Option from More Tools drop down");

			Thread.sleep(2000);
			System.out.println("Export window displayed :-" + wait
					.until(ExpectedConditions
							.visibilityOf(driver.findElement(By.xpath("//div[@id='dialog-for-export']"))))
					.isDisplayed());

			Select ddl_expFormat = new Select(
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("expFormat")))));
			ddl_expFormat.selectByValue("6");
			System.out.println("selected value for file type - 6");

			WebElement chk_IncludePricing = driver.findElement(By.xpath("//input[@id='expIncludePricingCheckBox']"));
			chk_IncludePricing.click();
			System.out.println("checked to Include Pricing");
			
			WebElement chk_IncludeStatus = driver.findElement(By.xpath("//input[@id='expIncludePSMSCheckBox']"));
			chk_IncludeStatus.click();
			System.out.println("checked to Include Status");

			WebElement btn_Export = wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//div/button[contains(.,'Export')]"))));
			Assert.assertEquals(btn_Export.isDisplayed(), true);
			btn_Export.click();
			System.out.println("clicked on Export");

			Thread.sleep(180000);

			WebElement lnk_Close = wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//div/a[contains(.,'Close')]"))));
			lnk_Close.click();

			Thread.sleep(2000);

			WebElement inp_Close = wait.until(
					ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div/input[@id='close']"))));
			inp_Close.click();
			System.out.println("Application closed");

		} catch (WebDriverException we) {
			we.printStackTrace();
			Assert.assertFalse(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true);
		}
		/*
		 * finally {
		 * 
		 * }
		 */
	}

	public static void loginSysco(WebDriver driver, String userName, String password) {

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
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='submit-button']/a"))));
		img_Login.click();

		System.out.println("Login Success");

	}

	public static void verifyLogin(WebDriver driver) {
		if (wait.until(
				ExpectedConditions.visibilityOf(driver.findElement((By.xpath("//td[@src='images/logo_sysco.png']")))))
				.isDisplayed()) {

			System.out.println("Login Success");
		}
	}

	public static boolean ifForceLoginRequired(WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			if (driver.findElement(By.xpath("//td/*/*[contains(.,'Already Logged In')]")).isDisplayed()) {
				System.out.println("User already logged in !!!");
				// driver.findElement(By.xpath("//input[contains(@type,'Password')]")).sendKeys("bocalago2016");
				// driver.findElement(By.xpath("//input[@value='Force
				// Login']")).click();
				// System.out.println("forced login");
			}
			return true;
		} catch (WebDriverException e) {
			e.printStackTrace();
			return false;
		}

	}

}
