package com.dell.webservice.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

public class AddOrderTest {
	
	String driverPath = "C:\\Users\\akashsinha\\eclipse-projects\\java-fsd-capstone-foodbox\\java-fsd-capstone-foodbox\\chromedriver\\chromedriver.exe";
	ChromeDriver driver;
	String baseUrl = "http://localhost:3000/";
	
	@Test
	void testCartUrl() {
		String siteUrl = baseUrl+"cart";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		assertEquals(siteUrl, driver.getCurrentUrl());
	}
	
	
	@Test
	void testOrderAdd_success() {
		String siteUrl = baseUrl+"signin";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		WebElement username = driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[1]/div/div/input");
		username.sendKeys("Alisha");
		WebElement password = driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[2]/div/div/input");
		password.sendKeys("pqr");
		ISelect role = new Select(driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[3]/div/div/select"));
		role.selectByVisibleText("As Admin");
		driver.findElementByXPath("/html/body/app-root/app-sign/div/form/button").click();
	
		String siteUrlNew = baseUrl+"cart";
		driver.get(siteUrlNew);
		WebElement addOrder = driver.findElementByXPath("/html/body/app-root/app-cart/div/button");
		addOrder.click();
		
		WebElement email = driver.findElementByXPath("/html/body/app-root/app-apporderdetails/div[2]/form/div[1]/div/div/input");
		email.sendKeys("Alisha.Panda@dell.com");
		WebElement address = driver.findElementByXPath("/html/body/app-root/app-apporderdetails/div[2]/form/div[2]/div/div/input");
		address.sendKeys("ABC Apartments");
		WebElement phone = driver.findElementByXPath("/html/body/app-root/app-apporderdetails/div[2]/form/div[3]/div/div/input");
		phone.sendKeys("845672345");
		
		WebElement addOrder1 = driver.findElementByXPath("/html/body/app-root/app-apporderdetails/div[2]/form/button");
		addOrder1.click();
		
		String siteUrlNew1 = baseUrl+"orderdetails";
		assertEquals(siteUrlNew1,driver.getCurrentUrl());
		
	}

}
