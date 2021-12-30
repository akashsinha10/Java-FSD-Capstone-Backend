package com.dell.webservice.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

public class LoginPageTest {
	
	String driverPath = "C:\\Users\\akashsinha\\eclipse-projects\\java-fsd-capstone-foodbox\\java-fsd-capstone-foodbox\\chromedriver\\chromedriver.exe";
	ChromeDriver driver;
//	String baseUrl = "http://foodbox-capstone.s3-website.us-east-2.amazonaws.com/";
	String baseUrl = "http://localhost:3000/";
	
	@Test
	void testLoginUrl() {
		String siteUrl = baseUrl+"signin";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		assertEquals(siteUrl, driver.getCurrentUrl());
	}
	
	@Test
	void testLogin_success() {
		String siteUrl = baseUrl+"signin";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		WebElement username = driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[1]/div/div/input");
		username.sendKeys("Avilasa");
		WebElement password = driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[2]/div/div/input");
		password.sendKeys("pqr");
		ISelect role = new Select(driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[3]/div/div/select"));
		role.selectByVisibleText("As Admin");
		driver.findElementByXPath("/html/body/app-root/app-sign/div/form/button").click();
		assertEquals(siteUrl,driver.getCurrentUrl());
	}

}
