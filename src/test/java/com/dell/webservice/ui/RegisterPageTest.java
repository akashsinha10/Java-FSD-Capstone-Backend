package com.dell.webservice.ui;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

public class RegisterPageTest {
	
	
	String driverPath = "C:\\Users\\akashsinha\\eclipse-projects\\java-fsd-capstone-foodbox\\java-fsd-capstone-foodbox\\chromedriver\\chromedriver.exe";
	ChromeDriver driver;
//	String baseUrl = "http://foodbox-capstone.s3-website.us-east-2.amazonaws.com/";
	String baseUrl = "http://localhost:3000/";
	
	@Test
	void testRegisterUrl() {
		String siteUrl = baseUrl+"signup";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		assertEquals(siteUrl, driver.getCurrentUrl());
	}
	
	@Test
	void testRegister_success() {
		String siteUrl = baseUrl+"signup";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		WebElement username = driver.findElementByXPath("/html/body/app-root/app-signupdata/div/form/div[1]/div/div/input");
		username.sendKeys("Avilasa");
		WebElement password = driver.findElementByXPath("/html/body/app-root/app-signupdata/div/form/div[2]/div/div/input");
		password.sendKeys("pqr");
		WebElement email = driver.findElementByXPath("/html/body/app-root/app-signupdata/div/form/div[3]/div/div/input");
		email.sendKeys("Av.Das@gmail.com");
		email.submit();
		ISelect role = new Select(driver.findElementByXPath("/html/body/app-root/app-signupdata/div/form/div[4]/div/div/select"));
		role.selectByVisibleText("As Admin");
		driver.findElementByXPath("/html/body/app-root/app-signupdata/div/form/button").click();
		assertEquals(siteUrl,driver.getCurrentUrl());
	}

}
