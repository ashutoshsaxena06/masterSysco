package com.edge.Sysco.online;

import javax.mail.MessagingException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.util.framework.CommonSysco;
import com.util.framework.RandomAction;
import com.util.framework.SendMailSSL;

public class TestSyscoOnline extends CommonSysco {
	public static String path = System.getProperty("user.home")+"\\Downloads\\chromedriver_win32\\chromedriver.exe";

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
		RandomAction.deleteFiles(System.getProperty("user.home")+"\\Downloads");
		driver = RandomAction.openBrowser("Chrome", path);
		System.out.println("Invoked browser .. ");
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
		startSysco(driver,"", "1200692", "032584003", "4Flowers");

		// sendMail
		// SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Boynton
		// Italian Bistro & Bar");
		SendMailSSL.sendMailActionCsvDE("Sysco - Offline GP", "Agliolio Restaurant");
		// SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Restaurant
		// Wellington");

	}

	
}
