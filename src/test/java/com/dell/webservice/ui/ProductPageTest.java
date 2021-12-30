package com.dell.webservice.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ProductPageTest {
	
	String driverPath = "C:\\Users\\akashsinha\\eclipse-projects\\java-fsd-capstone-foodbox\\java-fsd-capstone-foodbox\\chromedriver\\chromedriver.exe";
	ChromeDriver driver;
	String baseUrl = "http://localhost:3000/";
	
	@Test
	void testCartUrl() {
		String siteUrl = baseUrl+"products";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		assertEquals(siteUrl, driver.getCurrentUrl());
	}
	
	@Test
	void AddCart_success() {
		String siteUrl = baseUrl+"products";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		WebElement add = driver.findElementByXPath("/html/body/app-root/app-products/div[3]/table/tbody/tr[1]/td[7]/div/div[2]");
		add.click();
		assertEquals(siteUrl,driver.getCurrentUrl());
	}

}
