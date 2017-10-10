package com.edge.Sysco.online;

import javax.mail.MessagingException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.util.framework.RandomAction;
import com.util.framework.SendMailSSL;

public class TestSyscoOnline extends CommonSysco {

	@BeforeClass
	public void setup() {
		System.out.println("*************Sysco************");

	}

	@AfterClass
	public void AfterClass() {
		System.out.println("************End***********");
	}

	@BeforeMethod
	public void beforeTest() throws InterruptedException {
		System.out.println("***********StartTest*********");
		RandomAction.deleteFiles("C:\\Users\\Edge\\Downloads");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Edge\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver(options);

	}

	@AfterMethod
	public void pretearDown() {
		if (driver != null) {
			driver.quit();
			System.out.println("*******EndTest*********");

		}
	}


	@Test(priority = 1)
	public void Agliolio_Restaurant_Sysco() throws InterruptedException, MessagingException {

		System.out.println("1, Boynton_Sysco");

		// check if login is success
		startSysco(driver, "1200692", "032584003", "4Flowers");

		// sendMail
	//	SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Boynton Italian Bistro & Bar");
		SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Restaurant");
		//SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Restaurant Wellington");	
		
	}

//	@Test(priority = 2)
//	public void Wellington_Sysco() throws InterruptedException, MessagingException {
//
//		System.out.println("29, Wellington_Sysco");
//
//		// check if login is success
//		startSysco(driver, "1200698", "032452235", "4Flowers");
//
//		// sendMail
//		//SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Wellington Fresh Pasta & Wine");
//		SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Restaurant Wellington");
//	}

}
